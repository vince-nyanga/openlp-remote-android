package me.vincenyanga.openlpremote.domain.livedisplay

import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.LiveDisplayData
import java.lang.Exception

class GetLiveDisplayDataUseCase(val openLPApi: OpenLPApi): UseCase<Unit, LiveDisplayData>() {

    override fun execute(parameters: Unit): LiveDisplayData {
        val response = openLPApi.getLiveDisplayData().execute()
        if (response.isSuccessful){
            return response.body()!!.results
        }
        throw Exception("Failed to execute request")
    }
}