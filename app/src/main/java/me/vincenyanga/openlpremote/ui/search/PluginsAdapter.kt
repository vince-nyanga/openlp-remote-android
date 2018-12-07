package me.vincenyanga.openlpremote.ui.search

import android.content.Context
import android.widget.ArrayAdapter
import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.model.Plugin

class PluginsAdapter (context: Context): ArrayAdapter<Plugin>(context, R.layout.spinner_item, android.R.id.text1) {

    fun setData(plugins: List<Plugin>){
        clear()
        addAll(plugins)
        notifyDataSetChanged()
    }
}