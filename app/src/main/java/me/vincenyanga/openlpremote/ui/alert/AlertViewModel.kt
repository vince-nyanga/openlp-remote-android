package me.vincenyanga.openlpremote.ui.alert

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.vincenyanga.openlpremote.api.ApiFactory
import me.vincenyanga.openlpremote.domain.Result
import me.vincenyanga.openlpremote.domain.alert.DisplayAlertUseCase
import me.vincenyanga.openlpremote.model.Alert
import me.vincenyanga.openlpremote.model.RequestResult
import javax.inject.Inject

class AlertViewModel @Inject constructor(private val apiFactory: ApiFactory): ViewModel() {

    fun displayAlert(alert: Alert): LiveData<Result<RequestResult>>{
        return DisplayAlertUseCase(apiFactory.getApi())(alert)
    }
}