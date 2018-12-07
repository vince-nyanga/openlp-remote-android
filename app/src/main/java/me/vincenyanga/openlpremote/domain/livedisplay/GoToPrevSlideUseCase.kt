package me.vincenyanga.openlpremote.domain.livedisplay

import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.RequestResult
import java.lang.Exception

class GoToPrevSlideUseCase(private val openLPApi: OpenLPApi) : UseCase<Unit, RequestResult>() {

    override fun execute(parameters: Unit): RequestResult {
        val response = openLPApi.goToPreviousSlide().execute()
        if (response.isSuccessful) {
            return response.body()!!.results
        }
        throw Exception("Failed to execute request")
    }
}