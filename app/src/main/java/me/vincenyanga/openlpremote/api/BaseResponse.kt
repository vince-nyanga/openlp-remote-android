package me.vincenyanga.openlpremote.api

data class BaseResponse<out T> (val results: T)