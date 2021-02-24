package com.skillbox.permissionsanddate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_location.*
import org.threeten.bp.format.DateTimeFormatter

class LocationAdapter(
    private val onItemClicked: () -> Unit
) : ListAdapter<Location, LocationAdapter.Holder>(LocationDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflater(R.layout.item_location), onItemClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }


    class LocationDiffUtil : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }

    }

    class Holder(
        override val containerView: View,
        onItemClicked: () -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer{

        init {
            containerView.setOnClickListener {
                onItemClicked()
            }
        }

        private val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
                .withZone(org.threeten.bp.ZoneId.systemDefault())

        fun bind(location: Location) {
            locationTextView.text = location.location
            dateTextView.text = formatter.format(location.createdAt)
            Glide.with(itemView)
                .load(location.picture)
                .centerCrop()
                .into(locationImageView)
        }

    }

}

