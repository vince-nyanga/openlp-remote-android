package me.vincenyanga.openlpremote.api

data class BaseRequest<out T> (val request: T)