package me.vincenyanga.openlpremote.api

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(val username: String, val password: String) : Interceptor {

    private val credentials = Credentials.basic(username, password)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .addHeader("Authorization", credentials)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}