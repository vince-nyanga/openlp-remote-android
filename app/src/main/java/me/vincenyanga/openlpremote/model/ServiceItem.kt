package me.vincenyanga.openlpremote.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class ServiceItem(
    val id: String,
    val title: String,
    val plugin: String,
    val notes: String,
    @SerializedName("selected") val isSelected: Boolean,
    var selectionId: Int? = null)


class ServiceItemDiffCallback: DiffUtil.ItemCallback<ServiceItem>(){

    override fun areContentsTheSame(oldItem: ServiceItem, newItem: ServiceItem): Boolean {
        return oldItem.id === newItem.id
    }

    override fun areItemsTheSame(oldItem: ServiceItem, newItem: ServiceItem): Boolean {
        return oldItem.id === newItem.id
    }

}