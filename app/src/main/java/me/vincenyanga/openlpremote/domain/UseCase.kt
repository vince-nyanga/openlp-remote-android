package me.vincenyanga.openlpremote.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.vincenyanga.openlpremote.domain.internal.DefaltScheduler
import timber.log.Timber

/**
 * Executes business logic synchronously or asynchronously using a [Scheduler].
 */
abstract class UseCase<in P, R> {

    private val taskScheduler = DefaltScheduler

    /**
     * Executes the use case asynchronously and places the [Result] in a [MutableLiveData]
     *
     * @param parameters the input parameters to run the use case with.
     * @param result the [MutableLiveData] where the result is posted to.
     */
    operator fun invoke(parameters: P, result: MutableLiveData<Result<R>>) {
        result.value = Result.Loading
        try {
            taskScheduler.execute {
                try {
                    execute(parameters).let { useCaseResult ->
                        result.postValue(Result.Success(useCaseResult))
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    result.postValue(Result.Error(e))
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            result.postValue(Result.Error(e))
        }
    }

    /**
     * Executes the use case asynchronously and returns a [Result] in a new [LiveData] object
     *
     * @param parameters the input parameters to run the use case with
     *
     * @return an observable [LiveData] with a [Result]
     */
    operator fun invoke(parameters: P): LiveData<Result<R>> {
        val liveCallback: MutableLiveData<Result<R>> = MutableLiveData()
        this(parameters, liveCallback)
        return liveCallback
    }

    fun executeNow(parameters: P): Result<R> {
        return try {
            Result.Success(execute(parameters))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}

/**
 * Extension functions for [UseCase]
 */
operator fun <R> UseCase<Unit, R>.invoke(): LiveData<Result<R>> = this(Unit)

operator fun <R> UseCase<Unit, R>.invoke(result: MutableLiveData<Result<R>>) = this(Unit, result)