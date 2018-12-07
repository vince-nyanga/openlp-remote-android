package me.vincenyanga.openlpremote.domain.search

import com.google.gson.Gson
import me.vincenyanga.openlpremote.api.BaseRequest
import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.AddPluginParams

class AddToLiveUseCase(private val openLPApi: OpenLPApi): UseCase<AddPluginParams, Boolean> (){

    override fun execute(parameters: AddPluginParams): Boolean {
        val request = BaseRequest(parameters.data)
        val response = openLPApi.addToLive(parameters.plugin, Gson().toJson(request)).execute()
        if (response.isSuccessful){
            return true
        }
        throw Exception("Failed to execute request")
    }
}