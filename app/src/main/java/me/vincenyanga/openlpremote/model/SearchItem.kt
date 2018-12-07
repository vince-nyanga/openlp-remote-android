package me.vincenyanga.openlpremote.model

data class SearchItem (val text: String)
data class SearchParams(val plugin: String, val searchItem: SearchItem)