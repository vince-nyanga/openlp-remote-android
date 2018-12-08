package me.vincenyanga.openlpremote.ui.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import me.vincenyanga.openlpremote.R
import me.vincenyanga.openlpremote.model.ServiceItem
import me.vincenyanga.openlpremote.model.ServiceItemDiffCallback

fun MaterialButton.setRightIcon() { TextViewCompat.setCompoundDrawablesRelative(this, null, null, this.icon, null) }

class ServiceItemsAdapter(private val serviceItemCallbacks: ServiceItemCallbacks):
ListAdapter<ServiceItem, ServiceItemsAdapter.ServiceItemHolder>(ServiceItemDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceItemHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.service_item, parent, false)
        return ServiceItemHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceItemHolder, position: Int) {
      val item = getItem(position)
        item.selectionId = position
        holder.bind(item, serviceItemCallbacks)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ServiceItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val itemTitleText = itemView.findViewById<TextView>(R.id.itemTitle)
        private val itemImage = itemView.findViewById<ImageView>(R.id.itemImage)
        private val prevSlideButton = itemView.findViewById<MaterialButton>(R.id.previousSlideBtn)
        private val nextSlideButton = itemView.findViewById<MaterialButton>(R.id.nextSlideBtn)

        fun bind(serviceItem: ServiceItem, serviceItemCallbacks: ServiceItemCallbacks){
            nextSlideButton.setRightIcon()
            itemView.setOnClickListener { serviceItemCallbacks.onItemSelected(serviceItem) }
            nextSlideButton.setOnClickListener { serviceItemCallbacks.onNextSlideClicked() }
            prevSlideButton.setOnClickListener { serviceItemCallbacks.onPreviousSlideClicked() }

            itemTitleText.text = serviceItem.title
            itemView.findViewById<View>(R.id.selectedGroup).visibility = if(serviceItem.isSelected) View.VISIBLE else View.GONE
            if (serviceItem.isSelected){
                itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.selectedListViewItemColor))
                (itemView as MaterialCardView).cardElevation = 4f
            }else{
                itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.windowColor))
                (itemView as MaterialCardView).cardElevation = 0f
            }

            when {
                serviceItem.plugin == "songs" -> itemImage.setImageResource(R.drawable.ic_musical_note)
                serviceItem.plugin == "bibles" -> itemImage.setImageResource(R.drawable.ic_bible)
                else -> itemImage.setImageResource(R.drawable.ic_description_24dp)
            }

        }
    }
}