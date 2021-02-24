package com.skillbox.listsecond.recycler

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.listsecond.*
import com.skillbox.listsecond.adapters.VehicleAdapter
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator
import kotlinx.android.synthetic.main.fragment_vehicle.*
import kotlin.random.Random

class VehicleLinearFragment : Fragment(R.layout.fragment_vehicle), ItemList {

    private var vehicle: ArrayList<Vehicle> = arrayListOf()

    private var vehicleAdapter: VehicleAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            vehicle = savedInstanceState.getParcelableArrayList<Vehicle>(KEY_VEHICLE) ?: error("")
            if (vehicle.isEmpty()) {
                listEmptyTextView.visibility = View.VISIBLE
            } else listEmptyTextView.visibility = View.GONE
        }

        initLinearList()

        addFab.setOnClickListener {
            InfoDialogFragment()
                .show(childFragmentManager, "linearTag")
        }

        vehicleAdapter?.items = vehicle
    }

    private fun initLinearList() {
        vehicleAdapter =
            VehicleAdapter { position ->
                deleteVehicle(position)
            }
        with(vehicleList) {
            adapter = vehicleAdapter
            val linearLayout = LinearLayoutManager(requireContext())
            layoutManager = linearLayout
            linearLayout.reverseLayout = true
            linearLayout.stackFromEnd = true
            vehicleList.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayout) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?
                ) {
                    view?.post {
                        if (totalItemsCount < 200) {
                            vehicleAdapter?.items = loadVehicle(5)
                            vehicleAdapter?.notifyDataSetChanged()
                            linearLayout.reverseLayout = false
                            linearLayout.stackFromEnd = false
                        }
                    }
                }
            })
            setHasFixedSize(true)
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            addItemDecoration(ItemOffsetDecoration(requireContext()))
            itemAnimator = OvershootInLeftAnimator()
        }
    }

    private fun loadVehicle(count: Int): ArrayList<Vehicle> {
        val modelCar = listOf("BMW", "Audi", "Лада", "Ford")
        val modelPlane = listOf("Boeing", "Airbus", "Ан-30", "Fokker")
        val speedCar = listOf("250km/h", "220km/h", "200km/h", "190km/h")
        val speedPlane = listOf("1200km/h", "1000km/h", "1450km/h", "1320km/h")

        for (i in 0..count) {
            if (Random.nextInt(2) == 1) {
                vehicle.add(
                    Vehicle.Car(
                        id = Random.nextLong(),
                        model = modelCar.random(),
                        maxSpeed = speedCar.random(),
                        doorCount = Random.nextInt(2, 4).toString(),
                        pictureLink = Picture().pictureCar.random()
                    )
                )
            } else {
                vehicle.add(
                    Vehicle.Plane(
                        id = Random.nextLong(),
                        pictureLink = Picture().picturePlane.random(),
                        maxSpeed = speedPlane.random(),
                        maxHeight = Random.nextInt(10000, 12000).toString(),
                        model = modelPlane.random()
                    )
                )
            }
        }
        return vehicle
    }


    private fun deleteVehicle(position: Int) {
        vehicle = vehicle.filterIndexed { index, user -> index != position } as ArrayList<Vehicle>
        vehicleAdapter?.items = vehicle
        if (vehicle.isEmpty()) {
            listEmptyTextView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vehicleAdapter = null
    }

    override fun onItemCar(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        doorCount: String
    ) {
        vehicle.add(
            Vehicle.Car(
                id = id,
                model = model,
                maxSpeed = maxSpeed,
                pictureLink = pictureLink,
                doorCount = doorCount
            )
        )
        vehicleAdapter?.items = vehicle
        vehicleList.scrollToPosition(vehicle.lastIndex)
        if (vehicle.isNotEmpty()) {
            listEmptyTextView.visibility = View.GONE
        }
    }

    override fun onItemPlane(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        maxHeight: String
    ) {
        vehicle.add(
            Vehicle.Plane(
                id = id,
                model = model,
                maxSpeed = maxSpeed,
                pictureLink = pictureLink,
                maxHeight = maxHeight
            )
        )
        vehicleAdapter?.items = vehicle
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
        private const val KEY_VEHICLE = "vehicleLinear"
    }

}

