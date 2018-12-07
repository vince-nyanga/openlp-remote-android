package me.vincenyanga.openlpremote.domain.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.vincenyanga.openlpremote.api.BaseRequest
import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.ItemSelector
import me.vincenyanga.openlpremote.model.RequestResult
import java.lang.Exception

class SendItemToLiveUseCase(val openLPApi: OpenLPApi): UseCase<ItemSelector, RequestResult>() {

    override fun execute(parameters: ItemSelector): RequestResult {
        try {
            val request = BaseRequest(parameters)
            val requestString = Gson().toJson(request)
            val response = openLPApi.sendItemToLiveView(requestString).execute()
            if (response.isSuccessful){
               return response.body()!!.results
            }
            throw Exception("Failed to execute request.")
        }catch (e: Exception){
            throw Exception("Failed to execute request.")
        }
    }
}