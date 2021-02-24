package com.skillbox.listsecond.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.listsecond.R
import com.skillbox.listsecond.recycler.Vehicle
import com.skillbox.listsecond.inflate
import kotlinx.android.synthetic.main.fragment_plane.*

class PlaneAdapterDelegate (
    private val onItemClick: (position: Int) -> Unit
): AbsListItemAdapterDelegate<Vehicle.Plane, Vehicle, PlaneAdapterDelegate.PlaneHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): PlaneHolder {
        return PlaneHolder(
            parent.inflate(R.layout.fragment_plane),
            onItemClick
        )
    }

    override fun isForViewType(item: Vehicle, items: MutableList<Vehicle>, position: Int): Boolean {
        return item is Vehicle.Plane
    }

    override fun onBindViewHolder(
        item: Vehicle.Plane,
        holder: PlaneHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class PlaneHolder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseVehicleHolder(containerView, onItemClick) {

        fun bind(vehicle: Vehicle.Plane) {
            bindMainInfo(
                model = vehicle.model,
                speed = vehicle.maxSpeed,
                picture = vehicle.pictureLink
            )
            heightTextView.text = "Max height: ${vehicle.maxHeight}"
        }
    }
}