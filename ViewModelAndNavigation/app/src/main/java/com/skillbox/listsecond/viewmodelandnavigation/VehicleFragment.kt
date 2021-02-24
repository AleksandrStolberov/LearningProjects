package com.skillbox.listsecond.viewmodelandnavigation

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.listsecond.ItemList
import com.skillbox.listsecond.R
import com.skillbox.listsecond.adapters.VehicleAdapter
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator
import kotlinx.android.synthetic.main.fragment_vehicle.*

class VehicleFragment : Fragment(R.layout.fragment_vehicle), ItemList {

    private var vehicleAdapter: VehicleAdapter? = null

    private val vehicleViewModel: VehicleViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initLinearList()

        addFab.setOnClickListener {
            InfoDialogFragment()
                .show(childFragmentManager, "linearTag")
        }
        observeViewModelState()
    }

    private fun initLinearList() {
        vehicleAdapter =
            VehicleAdapter({ position ->
                deleteVehicle(position)
            }, { picture, model, speed ->
                val action =
                    VehicleFragmentDirections.actionVehicleFragmentToDetailsVehicleFragment(
                        model,
                        picture,
                        speed
                    )
                findNavController().navigate(action)
            }
            )
        with(vehicleList) {
            adapter = vehicleAdapter
            val linearLayout = LinearLayoutManager(requireContext())
            layoutManager = linearLayout
            setHasFixedSize(true)
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            itemAnimator = OvershootInLeftAnimator()
        }
    }


    private fun deleteVehicle(position: Int) {
        vehicleViewModel.deleteVehicle(position)
    }

    override fun onItemCar(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        doorCount: String
    ) {
        vehicleViewModel.addCar(id, model, maxSpeed, pictureLink, doorCount)
        vehicleList.postDelayed({
            vehicleList.scrollToPosition(0)
        }, 50)

    }

    override fun onItemPlane(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        maxHeight: String
    ) {
        vehicleViewModel.addPlane(id, model, maxSpeed, pictureLink, maxHeight)
        vehicleList.postDelayed({
            vehicleList.scrollToPosition(0)
        }, 50)
    }

    private fun observeViewModelState() {
        vehicleViewModel.vehicle
            .observe(viewLifecycleOwner) { newVehicle -> vehicleAdapter?.items = newVehicle }
        vehicleViewModel.showToast
            .observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Элемент удален", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vehicleAdapter = null
    }

}

