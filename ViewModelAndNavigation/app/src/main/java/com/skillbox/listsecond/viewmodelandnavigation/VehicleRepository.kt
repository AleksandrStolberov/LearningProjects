package com.skillbox.listsecond.viewmodelandnavigation

import com.skillbox.listsecond.Vehicle

class VehicleRepository {

    fun createCare(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        doorCount: String
    ): Vehicle {
           return Vehicle.Car(
                id = id,
                model = model,
                maxSpeed = maxSpeed,
                pictureLink = pictureLink,
                doorCount = doorCount
            )

    }

    fun createPlane(
        id: Long,
        model: String,
        maxSpeed: String,
        pictureLink: String,
        maxHeight: String
    ): Vehicle {
        return Vehicle.Plane(
                id = id,
                model = model,
                maxSpeed = maxSpeed,
                pictureLink = pictureLink,
                maxHeight = maxHeight
            )

    }

    fun deleteVehicle(vehicle: List<Vehicle>, position: Int): List<Vehicle> {
        return vehicle.filterIndexed { index, user -> index != position }
    }

}