package me.vincenyanga.openlpremote.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.vincenyanga.openlpremote.MainActivity
import me.vincenyanga.openlpremote.ui.search.SearchActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun searchActivity(): SearchActivity
}