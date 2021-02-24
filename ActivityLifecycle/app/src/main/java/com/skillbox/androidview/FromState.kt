package com.skillbox.androidview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class FromState(val message: String): Parcelable {

    fun getText(text: String): FromState{
        return copy(message = text)
    }


}