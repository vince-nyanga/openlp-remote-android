package me.vincenyanga.openlpremote.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import me.vincenyanga.openlpremote.OpenLPRemoteApplication
import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.api.ApiFactory
import me.vincenyanga.openlpremote.api.BasicAuthInterceptor
import me.vincenyanga.openlpremote.api.OpenLPApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: OpenLPRemoteApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesApiFactory(context: Context): ApiFactory {
      return ApiFactory(context)
    }
}