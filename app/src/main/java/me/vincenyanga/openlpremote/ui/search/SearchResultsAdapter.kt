package me.vincenyanga.openlpremote.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.model.SearchResult
import me.vincenyanga.openlpremote.model.SearchResultDiffCallback

class SearchResultsAdapter(private val clickListener: (View,SearchResult) -> Unit):
    ListAdapter<SearchResult, SearchResultHolder>(SearchResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchResultHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
       holder.bind(getItem(position), clickListener)
    }
}

class SearchResultHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val itemTitle = itemView.findViewById<TextView>(R.id.itemTitle)

    fun bind (item: SearchResult, clickListener: (View,SearchResult) -> Unit) {
        itemTitle.text = item.displayName
        itemView.setOnClickListener { clickListener(itemView,item) }
    }
}