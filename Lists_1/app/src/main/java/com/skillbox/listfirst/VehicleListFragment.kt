package com.skillbox.listfirst

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_vehicle.*

class VehicleListFragment : Fragment(R.layout.fragment_vehicle), ItemList {

    private var vehicle: ArrayList<Vehicle> = arrayListOf()

    private var vehicleAdapter by AutoClearedValue<VehicleAdapter>(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            vehicle = savedInstanceState.getParcelableArrayList<Vehicle>(KEY_VEHICLE) ?: error("")
            if (vehicle.isEmpty()) {
                listEmptyTextView.visibility = View.VISIBLE
            } else listEmptyTextView.visibility = View.GONE
        }

        initList()

        addFab.setOnClickListener {
            InfoDialogFragment()
                .show(childFragmentManager, "tag")
        }

        vehicleAdapter.upDateVehicle(vehicle)
        vehicleAdapter.notifyDataSetChanged()

    }

    private fun initList() {
        vehicleAdapter = VehicleAdapter { deletePerson(it) }
        with(vehicleList) {
            adapter = vehicleAdapter
            layoutManager = LinearLayoutManager(requireContext()).also {
                it.reverseLayout = true
                it.stackFromEnd = true
            }
            setHasFixedSize(true)
        }
    }

    private fun deletePerson(position: Int) {
        vehicle =
            vehicle.filterIndexed { index, vehicle -> index != position } as ArrayList<Vehicle>
        vehicleAdapter.upDateVehicle(vehicle)
        vehicleAdapter.notifyItemRemoved(position)
        if (vehicle.isEmpty()) {
            listEmptyTextView.visibility = View.VISIBLE
        }
    }

    override fun onItemCar(
        model: String,
        maxSpeed: String,
        pictureLink: String,
        doorCount: String
    ) {
        vehicle.add(
            Vehicle.Car(
                model = model, maxSpeed = maxSpeed, pictureLink = pictureLink, doorCount = doorCount
            )
        )
        vehicleAdapter.upDateVehicle(vehicle)
        vehicleAdapter.notifyItemInserted(vehicle.size)
        vehicleList.scrollToPosition(vehicle.lastIndex)
        if (vehicle.isNotEmpty()) {
            listEmptyTextView.visibility = View.GONE
        }
    }

    override fun onItemPlane(
        model: String,
        maxSpeed: String,
        pictureLink: String,
        maxHeight: String
    ) {
        vehicle.add(
            Vehicle.Plane(
                model = model, maxSpeed = maxSpeed, pictureLink = pictureLink, maxHeight = maxHeight
            )
        )
        vehicleAdapter.upDateVehicle(vehicle)
        vehicleAdapter.notifyItemInserted(vehicle.size)
        vehicleList.scrollToPosition(vehicle.lastIndex)
        if (vehicle.isNotEmpty()) {
            listEmptyTextView.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_VEHICLE, vehicle)
    }

    companion object {
        private const val KEY_VEHICLE = "vehicle"
    }

}