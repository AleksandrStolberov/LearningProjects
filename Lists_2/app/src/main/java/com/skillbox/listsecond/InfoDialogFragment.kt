package com.skillbox.listsecond

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.skillbox.listsecond.recycler.Picture
import kotlin.random.Random

class InfoDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val itemList: ItemList = parentFragment as ItemList
        val vehicleType = arrayOf("Car", "Plane")
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.vehicle)
            .setItems(vehicleType) { _, which ->
                if (which == 0) {
                    val viewCar: View =
                        LayoutInflater.from(requireContext())
                            .inflate(R.layout.dialog_car, null, false)
                    val carModel: EditText = viewCar.findViewById(R.id.editCarModel)
                    val carSpeed: EditText = viewCar.findViewById(R.id.editCarSpeed)
                    val carDoor: EditText = viewCar.findViewById(R.id.editCarDoor)
                    AlertDialog.Builder(requireActivity())
                        .setTitle(R.string.vehicle_par)
                        .setView(viewCar)
                        .setPositiveButton(R.string.ok) { _, _ ->
                            itemList.onItemCar(
                                id = Random.nextLong(),
                                model = carModel.text.toString(),
                                maxSpeed = carSpeed.text.toString(),
                                pictureLink = Picture().pictureCar.random(),
                                doorCount = carDoor.text.toString()
                            )
                        }
                        .create()
                        .show()

                } else {
                    val viewPlane: View =
                        LayoutInflater.from(requireContext())
                            .inflate(R.layout.dialog_plane, null, false)
                    val planeModel: EditText = viewPlane.findViewById(R.id.editPlaneModel)
                    val planeSpeed: EditText = viewPlane.findViewById(R.id.editPlaneSpeed)
                    val planeHeight: EditText = viewPlane.findViewById(R.id.editPlaneHeight)
                    AlertDialog.Builder(requireActivity())
                        .setTitle(R.string.vehicle_par)
                        .setView(viewPlane)
                        .setPositiveButton(R.string.ok) { _, _ ->
                            itemList.onItemPlane(
                                id = Random.nextLong(),
                                model = planeModel.text.toString(),
                                maxSpeed = planeSpeed.text.toString(),
                                pictureLink = Picture().picturePlane.random(),
                                maxHeight = planeHeight.text.toString()
                            )
                        }
                        .create()
                        .show()
                }
            }
            .create()
    }

}
