package me.vincenyanga.openlpremote.domain.search

import com.google.gson.Gson
import com.google.gson.JsonArray
import me.vincenyanga.openlpremote.api.BaseRequest
import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.SearchParams
import me.vincenyanga.openlpremote.model.SearchResult

class SearchUseCase(private val openLPApi: OpenLPApi): UseCase<SearchParams, List<SearchResult>>() {

    override fun execute(parameters: SearchParams): List<SearchResult> {
        val request = BaseRequest(parameters.searchItem)
        val response = openLPApi.search(parameters.plugin, Gson().toJson(request)).execute()
        if (response.isSuccessful) {
            val items = response.body()!!.results.getAsJsonArray("items")
            val results = mutableListOf<SearchResult>()
            items.forEach { item ->
                val data = (item as JsonArray)
                results.add(SearchResult(data[0].asInt, data[1].asString))
            }
            return results
        }
        else {
            throw Exception("Failed to execute request")
        }
    }
}