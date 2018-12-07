package me.vincenyanga.openlpremote.domain.livedisplay

import com.google.gson.Gson
import me.vincenyanga.openlpremote.api.BaseRequest
import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.ItemSelector
import me.vincenyanga.openlpremote.model.RequestResult
import java.lang.Exception

class SelectSlideUseCase(val openLPApi: OpenLPApi): UseCase<ItemSelector, RequestResult>() {
    override fun execute(parameters: ItemSelector): RequestResult {
        val request = BaseRequest(parameters)
        val response = openLPApi.selectSlide(Gson().toJson(request)).execute()
        if (response.isSuccessful){
            return response.body()!!.results
        }
        throw Exception("Failed to execute request")
    }
}