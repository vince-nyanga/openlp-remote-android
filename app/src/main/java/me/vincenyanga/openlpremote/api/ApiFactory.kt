package me.vincenyanga.openlpremote.api

import android.content.Context
import androidx.preference.PreferenceManager
import me.vincenyanga.openlpremote.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory(private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getApi(): OpenLPApi {
        val host = sharedPreferences.getString(context.getString(R.string.pref_host_key), "")
        val port = sharedPreferences.getString(context.getString(R.string.pref_port_key), "")

        val retrofit = Retrofit.Builder()
            .baseUrl("$host:$port")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        BasicAuthInterceptor(
                            sharedPreferences.getString(context.getString(R.string.pref_username_key), "")!!,
                            sharedPreferences.getString(context.getString(R.string.pref_password_key), "")!!
                        )
                    ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(OpenLPApi::class.java)
    }
}