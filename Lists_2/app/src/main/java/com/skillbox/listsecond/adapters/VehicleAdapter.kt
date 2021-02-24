package com.skillbox.listsecond.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.listsecond.recycler.Vehicle

class VehicleAdapter(
    private val onItemClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Vehicle>(VehicleDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(CarAdapterDelegate(onItemClick))
            .addDelegate(PlaneAdapterDelegate(onItemClick))
    }

    class VehicleDiffUtilCallback : DiffUtil.ItemCallback<Vehicle>() {
        override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return when {
                oldItem is Vehicle.Car && newItem is Vehicle.Car -> oldItem.id == newItem.id
                oldItem is Vehicle.Plane && newItem is Vehicle.Plane -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem == newItem
        }
    }
}