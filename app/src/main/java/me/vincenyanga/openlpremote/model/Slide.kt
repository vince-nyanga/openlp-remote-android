package me.vincenyanga.openlpremote.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class Slide (
    val tag: String,
    val text: String,
    val html: String,
    @SerializedName("selected") val isSelected: Boolean,
    var selectionId: Int? = null)

class SlideDiffCallback: DiffUtil.ItemCallback<Slide>(){
    override fun areItemsTheSame(oldItem: Slide, newItem: Slide): Boolean {
        return oldItem.tag === newItem.tag
    }

    override fun areContentsTheSame(oldItem: Slide, newItem: Slide): Boolean {
        return oldItem.tag === newItem.tag
    }

}