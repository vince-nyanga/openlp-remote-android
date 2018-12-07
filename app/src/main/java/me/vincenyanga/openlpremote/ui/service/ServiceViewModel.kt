package me.vincenyanga.openlpremote.ui.service

import me.vincenyanga.openlpremote.domain.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel;
import me.vincenyanga.openlpremote.api.ApiFactory
import me.vincenyanga.openlpremote.api.BaseResponse
import me.vincenyanga.openlpremote.domain.invoke
import me.vincenyanga.openlpremote.domain.livedisplay.GoToNextSlideUseCase
import me.vincenyanga.openlpremote.domain.livedisplay.GoToPrevSlideUseCase
import me.vincenyanga.openlpremote.domain.service.GetServiceDataUseCase
import me.vincenyanga.openlpremote.domain.service.SendItemToLiveUseCase
import me.vincenyanga.openlpremote.model.ItemSelector
import me.vincenyanga.openlpremote.model.RequestResult
import me.vincenyanga.openlpremote.model.ServiceData
import javax.inject.Inject

class ServiceViewModel @Inject constructor(private val apiFactory: ApiFactory) : ViewModel() {

    val serviceData: MutableLiveData<Result<ServiceData>> = MutableLiveData()

    private var data: LiveData<Result<ServiceData>>? =null

    private val observer: Observer<Result<ServiceData>> = Observer{
        serviceData.value = it
    }

    fun refresh(){
        data?.removeObserver(observer)
        data = GetServiceDataUseCase(apiFactory.getApi())()
        data?.observeForever(observer)
    }

    fun selectItem(id: Int): LiveData<Result<RequestResult>>{
        return SendItemToLiveUseCase(apiFactory.getApi())(ItemSelector(id))
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
