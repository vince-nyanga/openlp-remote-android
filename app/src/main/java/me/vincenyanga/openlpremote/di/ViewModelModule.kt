package me.vincenyanga.openlpremote.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.vincenyanga.openlpremote.ui.alert.AlertViewModel
import me.vincenyanga.openlpremote.ui.live.LiveViewViewModel
import me.vincenyanga.openlpremote.ui.search.SearchViewModel
import me.vincenyanga.openlpremote.ui.service.ServiceViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: OpenLPViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ServiceViewModel::class)
    abstract fun bindsServiceViewModel(viewModel: ServiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LiveViewViewModel::class)
    abstract fun bindsLiveViewViewModel(viewModel: LiveViewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlertViewModel::class)
    abstract fun bindsAlertViewModel(viewModel: AlertViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindsSearchViewModel(viewModel: SearchViewModel): ViewModel
}