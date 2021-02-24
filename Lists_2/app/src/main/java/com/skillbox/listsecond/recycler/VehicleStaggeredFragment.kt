package com.skillbox.listsecond.recycler

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.skillbox.listsecond.InfoDialogFragment
import com.skillbox.listsecond.ItemList
import com.skillbox.listsecond.ItemOffsetDecoration
import com.skillbox.listsecond.R
import com.skillbox.listsecond.adapters.VehicleAdapter
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_vehicle.*

class VehicleStaggeredFragment : Fragment(R.layout.fragment_vehicle), ItemList {

    private var vehicleStaggered: ArrayList<Vehicle> = arrayListOf()


    private var vehicleAdapterStaggered: VehicleAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            vehicleStaggered =
                savedInstanceState.getParcelableArrayList<Vehicle>(KEY_STAGGERED) ?: error("")
            if (vehicleStaggered.isEmpty()) {
                listEmptyTextView.visibility = View.VISIBLE
            } else listEmptyTextView.visibility = View.GONE
        }

        initStaggeredList()

        addFab.setOnClickListener {
            InfoDialogFragment()
                .show(childFragmentManager, "linearTag")
        }

        vehicleAdapterStaggered?.items = vehicleStaggered
    }

    private fun initStaggeredList() {
        vehicleAdapterStaggered =
            VehicleAdapter { position ->
                deleteVehicleGrid(position)
            }
        with(vehicleList) {
            adapter = vehicleAdapterStaggered
            layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL).apply {
                    reverseLayout = true
                }
            setHasFixedSize(true)
            addItemDecoration(ItemOffsetDecoration(requireContext()))
            itemAnimator = ScaleInAnimator()
        }
    }

    private fun deleteVehicleGrid(position: Int) {
        vehicleStaggered =
            vehicleStaggered.filterIndexed { index, user -> index != position } as ArrayList<Vehicle>
        vehicleAdapterStaggered?.items = vehicleStaggered
        if (vehicleStaggered.isEmpty()) {
            listEmptyTextView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vehicleAdapterStaggered = null
    }

    override fun onItemCar(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        doorCount: String
    ) {
        vehicleStaggered.add(
            Vehicle.Car(
                id = id,
                model = model,
                maxSpeed = maxSpeed,
                pictureLink = pictureLink,
                doorCount = doorCount
            )
        )
        vehicleAdapterStaggered?.items = vehicleStaggered
        vehicleList.scrollToPosition(vehicleStaggered.lastIndex)
        if (vehicleStaggered.isNotEmpty()) {
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
        vehicleStaggered.add(
            Vehicle.Plane(
                id = id,
                model = model,
                maxSpeed = maxSpeed,
                pictureLink = pictureLink,
                maxHeight = maxHeight
            )
        )
        vehicleAdapterStaggered?.items = vehicleStaggered
        vehicleList.scrollToPosition(vehicleStaggered.lastIndex)
        if (vehicleStaggered.isNotEmpty()) {
            listEmptyTextView.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_STAGGERED, vehicleStaggered)
    }

    companion object {
        private const val KEY_STAGGERED = "vehicleGrid"
    }

}