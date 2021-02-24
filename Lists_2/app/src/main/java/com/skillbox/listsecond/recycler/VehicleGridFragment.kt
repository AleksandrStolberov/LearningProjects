package com.skillbox.listsecond.recycler

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.skillbox.listsecond.InfoDialogFragment
import com.skillbox.listsecond.ItemList
import com.skillbox.listsecond.ItemOffsetDecoration
import com.skillbox.listsecond.R
import com.skillbox.listsecond.adapters.VehicleAdapter
import jp.wasabeef.recyclerview.animators.FlipInLeftYAnimator
import kotlinx.android.synthetic.main.fragment_vehicle.*

class VehicleGridFragment : Fragment(R.layout.fragment_vehicle), ItemList {

    private var vehicleGrid: ArrayList<Vehicle> = arrayListOf()


    private var vehicleAdapterGrid: VehicleAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            vehicleGrid = savedInstanceState.getParcelableArrayList<Vehicle>(KEY_GRID) ?: error("")
            if (vehicleGrid.isEmpty()) {
                listEmptyTextView.visibility = View.VISIBLE
            } else listEmptyTextView.visibility = View.GONE
        }

        initGridList()

        addFab.setOnClickListener {
            InfoDialogFragment()
                .show(childFragmentManager, "linearTag")
        }

        vehicleAdapterGrid?.items = vehicleGrid
    }

    private fun initGridList() {
        vehicleAdapterGrid =
            VehicleAdapter { position ->
                deleteVehicleGrid(position)
            }
        with(vehicleList) {
            adapter = vehicleAdapterGrid
            layoutManager =
                GridLayoutManager(requireContext(), 4, GridLayoutManager.HORIZONTAL, true)
            setHasFixedSize(true)
            addItemDecoration(ItemOffsetDecoration(requireContext()))
            itemAnimator = FlipInLeftYAnimator()
        }
    }


    private fun deleteVehicleGrid(position: Int) {
        vehicleGrid =
            vehicleGrid.filterIndexed { index, user -> index != position } as ArrayList<Vehicle>
        vehicleAdapterGrid?.items = vehicleGrid
        if (vehicleGrid.isEmpty()) {
            listEmptyTextView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vehicleAdapterGrid = null
    }

    override fun onItemCar(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        doorCount: String
    ) {
        vehicleGrid.add(
            Vehicle.Car(
                id = id,
                model = model,
                maxSpeed = maxSpeed,
                pictureLink = pictureLink,
                doorCount = doorCount
            )
        )
        vehicleAdapterGrid?.items = vehicleGrid
        vehicleList.scrollToPosition(vehicleGrid.lastIndex)
        if (vehicleGrid.isNotEmpty()) {
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
        vehicleGrid.add(
            Vehicle.Plane(
                id = id,
                model = model,
                maxSpeed = maxSpeed,
                pictureLink = pictureLink,
                maxHeight = maxHeight
            )
        )
        vehicleAdapterGrid?.items = vehicleGrid
        vehicleList.scrollToPosition(vehicleGrid.lastIndex)
        if (vehicleGrid.isNotEmpty()) {
            listEmptyTextView.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_GRID, vehicleGrid)
    }

    companion object {
        private const val KEY_GRID = "vehicleGrid"
    }

}
