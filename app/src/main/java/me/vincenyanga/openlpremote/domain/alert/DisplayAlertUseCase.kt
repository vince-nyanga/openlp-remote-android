package me.vincenyanga.openlpremote.domain.alert

import com.google.gson.Gson
import me.vincenyanga.openlpremote.api.BaseRequest
import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.Alert
import me.vincenyanga.openlpremote.model.RequestResult
import java.lang.Exception

class DisplayAlertUseCase(private val openLPApi: OpenLPApi): UseCase<Alert,RequestResult>() {

    override fun execute(parameters: Alert): RequestResult {
        val request = BaseRequest(parameters)
        val response = openLPApi.displayAlert(Gson().toJson(request)).execute()
        if (response.isSuccessful){
            return response.body()!!.results
        }else{
            throw Exception("Failed to execute request")
        }
    }
}