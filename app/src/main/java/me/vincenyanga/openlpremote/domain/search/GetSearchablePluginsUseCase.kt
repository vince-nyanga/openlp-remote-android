package me.vincenyanga.openlpremote.domain.search

import com.google.gson.JsonArray
import me.vincenyanga.openlpremote.api.OpenLPApi
import me.vincenyanga.openlpremote.domain.UseCase
import me.vincenyanga.openlpremote.model.Plugin


class GetSearchablePluginsUseCase(private val openLPApi: OpenLPApi): UseCase<Unit, List<Plugin>>() {

    override fun execute(parameters: Unit): List<Plugin> {
        val response = openLPApi.getSearchablePlugins().execute()
        if (response.isSuccessful){
            val jsonObject = response.body()!!.results
            val items = jsonObject.getAsJsonArray("items")
            val plugins = mutableListOf<Plugin>()
            items.forEach{ item  ->
                val data = (item as JsonArray)
                plugins.add(Plugin(data[0].asString, data[1].asString))
            }
            return plugins
        }
        else {
            throw Exception("Failed to execute request")
        }
    }
}