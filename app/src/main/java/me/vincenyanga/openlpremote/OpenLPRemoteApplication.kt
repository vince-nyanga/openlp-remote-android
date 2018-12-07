package me.vincenyanga.openlpremote

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import me.vincenyanga.openlpremote.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree


class OpenLPRemoteApplication : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.builder().create(this)
    }
}