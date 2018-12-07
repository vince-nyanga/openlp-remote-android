package me.vincenyanga.openlpremote.ui.service

import me.vincenyanga.openlpremote.model.ServiceItem

interface ServiceItemCallbacks {
    fun onItemSelected(item: ServiceItem)
    fun onNextSlideClicked()
    fun onPreviousSlideClicked()
}