package com.skillbox.listsecond.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.listsecond.R
import com.skillbox.listsecond.recycler.Vehicle
import com.skillbox.listsecond.inflate
import kotlinx.android.synthetic.main.fragment_car.*

class CarAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit
): AbsListItemAdapterDelegate<Vehicle.Car, Vehicle, CarAdapterDelegate.CarHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): CarHolder {
        return CarHolder(parent.inflate(R.layout.fragment_car), onItemClick)
    }

    override fun isForViewType(item: Vehicle, items: MutableList<Vehicle>, position: Int): Boolean {
        return item is Vehicle.Car
    }

    override fun onBindViewHolder(
        item: Vehicle.Car,
        holder: CarHolder,
        payloads: MutableList<Any>
    ) {
       holder.bind(item)
    }

    class CarHolder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseVehicleHolder(containerView, onItemClick) {

        fun bind(vehicle: Vehicle.Car) {
            bindMainInfo(
                model = vehicle.model,
                speed = vehicle.maxSpeed,
                picture = vehicle.pictureLink
            )
            doorTextView.text = "Door count: ${vehicle.doorCount}"
        }
    }
}