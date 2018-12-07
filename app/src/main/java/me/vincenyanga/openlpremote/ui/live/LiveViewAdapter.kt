package me.vincenyanga.openlpremote.ui.live


import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.model.Slide
import me.vincenyanga.openlpremote.model.SlideDiffCallback

class LiveViewAdapter(private val onSlideClickListener: (Slide) -> Unit) :
    ListAdapter<Slide, SlideHolder>(SlideDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.live_view_item, parent, false)
        return SlideHolder(view)
    }

    override fun onBindViewHolder(holder: SlideHolder, position: Int) {
        val slide = getItem(position)
        slide.selectionId = position
        holder.bind(slide, onSlideClickListener)
    }
}

@Suppress("DEPRECATION")
class SlideHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val itemTitleText = itemView.findViewById<TextView>(R.id.itemTitle)

    fun bind(slide: Slide, onSlideClickListener: (Slide) -> Unit) {
        itemView.setOnClickListener { onSlideClickListener(slide) }

        itemTitleText.text = Html.fromHtml(slide.html)

        if (slide.isSelected) {
            itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.selectedListViewItemColor))
        } else {
            itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.windowColor))
        }
    }
}