package com.skillbox.listfirst

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Vehicle : Parcelable {

    @Parcelize
    data class Car(
        val model: String,
        val maxSpeed: String,
        val pictureLink: String,
        val doorCount: String
    ) : Vehicle(), Parcelable

    @Parcelize
    data class Plane(
        val model: String,
        val maxSpeed: String,
        val pictureLink: String,
        val maxHeight: String
    ) : Vehicle(), Parcelable
}