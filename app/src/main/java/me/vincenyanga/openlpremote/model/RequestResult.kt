package me.vincenyanga.openlpremote.model

import com.google.gson.annotations.SerializedName

data class RequestResult(@SerializedName("success")val isSuccessful: Boolean)