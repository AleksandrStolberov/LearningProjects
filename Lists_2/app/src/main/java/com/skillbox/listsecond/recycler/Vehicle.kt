package com.skillbox.listsecond.recycler

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Vehicle : Parcelable {

    @Parcelize
    data class Car(
        val id: Long,
        val model: String,
        val maxSpeed: String,
        val pictureLink: String,
        val doorCount: String
    ) : Vehicle(), Parcelable

    @Parcelize
    data class Plane(
        val id: Long,
        val model: String,
        val maxSpeed: String,
        val pictureLink: String,
        val maxHeight: String
    ) : Vehicle(), Parcelable
}