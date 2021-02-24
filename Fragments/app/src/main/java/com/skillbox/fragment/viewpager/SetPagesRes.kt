package com.skillbox.fragment.viewpager

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SetPagesRes(
    @StringRes val articleRes: Int,
    @StringRes val nameRes: Int,
    @ColorRes val bgColorRes: Int,
    @DrawableRes val drawableRes: Int,
    val tags: List<ArticleTag>
) : Parcelable