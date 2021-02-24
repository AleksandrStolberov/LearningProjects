package com.skillbox.listfirst

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VehicleAdapter(
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var vehicle: ArrayList<Vehicle> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CAR -> CarHolder(parent.inflate(R.layout.fragment_car), onItemClick)
            TYPE_PLANE -> PlaneHolder(parent.inflate(R.layout.fragment_plane), onItemClick)
            else -> error("Not view holder $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (vehicle[position]) {
            is Vehicle.Car -> TYPE_CAR
            is Vehicle.Plane -> TYPE_PLANE
        }

    }

    override fun getItemCount(): Int {
        return vehicle.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CarHolder -> {
                val vehicle = vehicle[position].let { it as? Vehicle.Car }
                    ?: error("Vehicle not position $position not car")
                holder.bind(vehicle)
            }
            is PlaneHolder -> {
                val vehicle = vehicle[position].let { it as? Vehicle.Plane }
                    ?: error("Vehicle not position $position not plane")
                holder.bind(vehicle)
            }
            else -> error("IncorrectViewHolder")
        }
    }

    fun upDateVehicle(newVehicle: ArrayList<Vehicle>) {
        vehicle = newVehicle
    }


    abstract class BaseHolder(
        view: View,
        private val onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val modelTextView: TextView = view.findViewById(R.id.modelTextView)
        private val speedTextView: TextView = view.findViewById(R.id.speedTextView)
        private val pictureImageView: ImageView = view.findViewById(R.id.pictureImageView)

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        protected fun bindMainInfo(
            model: String,
            speed: String,
            picture: String
        ) {
            modelTextView.text = "Model: $model"
            speedTextView.text = "Max speed: $speed"
            Glide
                .with(itemView)
                .load(picture)
                .centerCrop()
                .error(R.drawable.ic_baseline_signal_off)
                .placeholder(R.drawable.ic_baseline_portrait)
                .into(pictureImageView)
        }
    }

    class CarHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseHolder(view, onItemClick) {

        private val doorTextView: TextView = view.findViewById(R.id.doorTextView)

        fun bind(vehicle: Vehicle.Car) {
            bindMainInfo(
                model = vehicle.model,
                speed = vehicle.maxSpeed,
                picture = vehicle.pictureLink
            )
            doorTextView.text = "Door count: ${vehicle.doorCount}"
        }
    }

    class PlaneHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseHolder(view, onItemClick) {

        private val heightTextView: TextView = view.findViewById(R.id.heightTextView)

        fun bind(vehicle: Vehicle.Plane) {
            bindMainInfo(
                model = vehicle.model,
                speed = vehicle.maxSpeed,
                picture = vehicle.pictureLink
            )
            heightTextView.text = "Max height: ${vehicle.maxHeight}"
        }
    }

    companion object {
        private const val TYPE_CAR = 1
        private const val TYPE_PLANE = 2
    }
}