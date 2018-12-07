package me.vincenyanga.openlpremote.domain.service

import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.ServiceData
import java.lang.Exception

class GetServiceDataUseCase(val openLPApi: OpenLPApi) : UseCase<Unit, ServiceData>() {

    override fun execute(parameters: Unit): ServiceData {
        val response = openLPApi.getServiceData().execute()
        if (response.isSuccessful){
            return response.body()!!.results
        }
        throw Exception("Failed to process request. Please check your connection")
    }
}