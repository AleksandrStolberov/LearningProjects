package com.skillbox.listsecond.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.listsecond.R
import kotlinx.android.extensions.LayoutContainer

abstract class BaseVehicleHolder(
    view: View,
    private val onItemClick: (position: Int) -> Unit,
    private val detailClick: (picture: String, model: String, speed: String) -> Unit
) : RecyclerView.ViewHolder(view), LayoutContainer {

    private val modelTextView: TextView = view.findViewById(R.id.modelTextView)
    private val speedTextView: TextView = view.findViewById(R.id.speedTextView)
    private val pictureImageView: ImageView = view.findViewById(R.id.pictureImageView)

    init {
        view.setOnClickListener {
            detailClick(pictureInfo, modelInfo, speedInfo)
        }
        view.setOnLongClickListener{
            onItemClick(adapterPosition)
            true
        }
    }

    private var pictureInfo = ""
    private var modelInfo = ""
    private var speedInfo = ""

    protected fun bindMainInfo(
        model: String,
        speed: String,
        picture: String
    ) {
        modelTextView.text = "Model: $model"
        speedTextView.text = "Max speed: $speed"
        Glide
            .with(itemView)
            .load(picture)
            .centerCrop()
            .error(R.drawable.ic_baseline_signal_off)
            .placeholder(R.drawable.ic_baseline_portrait)
            .into(pictureImageView)

        pictureInfo = picture
        modelInfo = model
        speedInfo = speed
    }
}