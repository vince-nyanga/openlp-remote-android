package me.vincenyanga.openlpremote.model

import androidx.recyclerview.widget.DiffUtil

data class SearchResult(val id: Int, val displayName: String)

class  SearchResultDiffCallback: DiffUtil.ItemCallback<SearchResult>(){

    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem.id == newItem.id
    }


}