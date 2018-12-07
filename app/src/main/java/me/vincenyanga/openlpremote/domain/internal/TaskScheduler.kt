package me.vincenyanga.openlpremote.domain.internal

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

interface Scheduler {

    fun execute(task: () -> Unit)

    fun postToUiThread(task: () -> Unit)

    fun postDelayedToUiThread(delay: Long, task: () -> Unit)
}

/**
 * Runs tasks in a [ExecutorService] with a fixed thread pool
 */
internal object AsyncScheduler : Scheduler {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2)


    override fun execute(task: () -> Unit) {
        executorService.execute(task)
    }

    override fun postToUiThread(task: () -> Unit) {
        if (isUiThread()){
            task()
        } else {
            val uiThreadHandler = Handler(Looper.getMainLooper())
            uiThreadHandler.post(task)
        }
    }

    override fun postDelayedToUiThread(delay: Long, task: () -> Unit) {
        val uiThreadHandler = Handler(Looper.getMainLooper())
        uiThreadHandler.postDelayed(task, delay)
    }


    private fun isUiThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }

}

/**
 * Runs tasks synchronously. Useful for testing
 */
object SyncScheduler : Scheduler {
    private val postDelayedTasks = mutableListOf<() -> Unit>()

    override fun execute(task: () -> Unit) {
        task()
    }

    override fun postToUiThread(task: () -> Unit) {
        task()
    }

    override fun postDelayedToUiThread(delay: Long, task: () -> Unit) {
        postDelayedTasks.add(task)
    }

    fun runAllScheduledPostDelayedTasks() {
        val tasks = postDelayedTasks.toList()
        clearScheduledPostdelayedTasks()
        for (task in tasks) {
            task()
        }
    }

    fun clearScheduledPostdelayedTasks() {
        postDelayedTasks.clear()
    }
}

/**
 * A [Scheduler] that by default handles operations in the [AsyncScheduler]
 */
object DefaltScheduler : Scheduler {
    private var delegate: Scheduler = AsyncScheduler

    fun setDelegate(newDelegate: Scheduler?) {
        delegate = newDelegate ?: AsyncScheduler
    }

    override fun execute(task: () -> Unit) {
        delegate.execute(task)
    }

    override fun postToUiThread(task: () -> Unit) {
        delegate.postToUiThread(task)
    }

    override fun postDelayedToUiThread(delay: Long, task: () -> Unit) {
        delegate.postDelayedToUiThread(delay,task)
    }
}