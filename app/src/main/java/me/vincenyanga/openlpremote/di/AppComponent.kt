package me.vincenyanga.openlpremote.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.vincenyanga.openlpremote.OpenLPRemoteApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<OpenLPRemoteApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<OpenLPRemoteApplication>()
}