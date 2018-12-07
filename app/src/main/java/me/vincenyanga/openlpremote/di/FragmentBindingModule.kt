package me.vincenyanga.openlpremote.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.vincenyanga.openlpremote.ui.live.LiveViewFragment
import me.vincenyanga.openlpremote.ui.service.ServiceFragment

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    internal abstract fun serviceFragment(): ServiceFragment

    @ContributesAndroidInjector
    internal abstract fun liveViewFragment(): LiveViewFragment
}