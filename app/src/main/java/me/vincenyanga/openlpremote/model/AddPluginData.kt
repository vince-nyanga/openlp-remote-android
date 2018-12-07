package me.vincenyanga.openlpremote.model

data class AddPluginData (val id: Int)

data class AddPluginParams(val plugin: String, val data: AddPluginData)