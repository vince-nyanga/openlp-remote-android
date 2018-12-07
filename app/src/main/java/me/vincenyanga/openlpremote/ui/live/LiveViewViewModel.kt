package me.vincenyanga.openlpremote.ui.live

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import me.vincenyanga.openlpremote.api.ApiFactory
import me.vincenyanga.openlpremote.domain.Result
import me.vincenyanga.openlpremote.domain.invoke
import me.vincenyanga.openlpremote.domain.livedisplay.GetLiveDisplayDataUseCase
import me.vincenyanga.openlpremote.domain.livedisplay.GoToNextSlideUseCase
import me.vincenyanga.openlpremote.domain.livedisplay.GoToPrevSlideUseCase
import me.vincenyanga.openlpremote.domain.livedisplay.SelectSlideUseCase
import me.vincenyanga.openlpremote.model.ItemSelector
import me.vincenyanga.openlpremote.model.LiveDisplayData
import me.vincenyanga.openlpremote.model.RequestResult
import javax.inject.Inject


class LiveViewViewModel @Inject constructor(private val apiFactory: ApiFactory) : ViewModel() {

    val liveViewData: MutableLiveData<Result<LiveDisplayData>> = MutableLiveData()
    private var data: LiveData<Result<LiveDisplayData>>? =null
    private val observer: Observer<Result<LiveDisplayData>> = Observer{
        liveViewData.value = it
    }

    fun refresh(){
        data?.removeObserver(observer)
        data = GetLiveDisplayDataUseCase(apiFactory.getApi())()
        data?.observeForever(observer)
    }

    fun selectSlide(id: Int): LiveData<Result<RequestResult>>{
        return SelectSlideUseCase(apiFactory.getApi())(ItemSelector(id))
    }

    fun goToNextSlide(){
        GoToNextSlideUseCase(apiFactory.getApi())()
    }

    fun goToPrevSlide(){
        GoToPrevSlideUseCase(apiFactory.getApi())()
    }

    override fun onCleared() {
        super.onCleared()
        data?.removeObserver(observer)
    }
}
