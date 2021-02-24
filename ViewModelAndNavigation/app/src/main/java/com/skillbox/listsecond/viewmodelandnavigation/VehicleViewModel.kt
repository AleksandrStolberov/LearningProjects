package com.skillbox.listsecond.viewmodelandnavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.listsecond.SingleLiveEvent
import com.skillbox.listsecond.Vehicle

class VehicleViewModel: ViewModel() {

    private val vehicleLiveData = MutableLiveData<List<Vehicle>>(emptyList())

    val vehicle: LiveData<List<Vehicle>>
      get() = vehicleLiveData

    private val toastLiveData =
        SingleLiveEvent<Unit>()
    val showToast: LiveData<Unit>
        get() = toastLiveData

    private val repository = VehicleRepository()

    fun addCar(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        doorCount: String
    ) {
        val newCar = repository.createCare(id, model, maxSpeed, pictureLink, doorCount)
        val updateCar = listOf(newCar) + vehicleLiveData.value.orEmpty()
        vehicleLiveData.postValue(updateCar)
    }

    fun addPlane(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        maxHeight: String
    ){
        val newPlane = repository.createPlane(id, model, maxSpeed, pictureLink, maxHeight)
        val updatePlane = listOf(newPlane) + vehicleLiveData.value.orEmpty()
        vehicleLiveData.postValue(updatePlane)
    }

    fun deleteVehicle(position: Int) {
        vehicleLiveData.postValue(repository.deleteVehicle(vehicleLiveData.value.orEmpty(), position))
        toastLiveData.postValue(Unit)
    }
}