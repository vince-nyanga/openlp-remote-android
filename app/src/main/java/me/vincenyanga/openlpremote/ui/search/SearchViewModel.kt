package me.vincenyanga.openlpremote.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.vincenyanga.openlpremote.api.ApiFactory
import me.vincenyanga.openlpremote.domain.Result
import me.vincenyanga.openlpremote.domain.invoke
import me.vincenyanga.openlpremote.domain.search.AddToLiveUseCase
import me.vincenyanga.openlpremote.domain.search.AddToServiceUseCase
import me.vincenyanga.openlpremote.domain.search.GetSearchablePluginsUseCase
import me.vincenyanga.openlpremote.domain.search.SearchUseCase
import me.vincenyanga.openlpremote.model.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val apiFactory: ApiFactory): ViewModel() {

    fun getPlugins(): LiveData<Result<List<Plugin>>>{
        return GetSearchablePluginsUseCase(apiFactory.getApi())()
    }

    fun search(plugin: String, text: String): LiveData<Result<List<SearchResult>>>{
        return SearchUseCase(apiFactory.getApi())(SearchParams(plugin, SearchItem(text)))
    }

    fun addToService(plugin: String, id: Int): LiveData<Result<Boolean>>{
        return AddToServiceUseCase(apiFactory.getApi())(AddPluginParams(plugin, AddPluginData(id)))
    }

    fun addToLive(plugin: String, id: Int): LiveData<Result<Boolean>>{
        return AddToLiveUseCase(apiFactory.getApi())(AddPluginParams(plugin, AddPluginData(id)))
    }
}