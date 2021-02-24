package com.skillbox.permissionsanddate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Instant

@Parcelize
data class Location(
    val id: Long,
    val createdAt: Instant,
    val location: String,
    val picture: String
): Parcelable