package com.skillbox.listsecond.viewmodelandnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.skillbox.listsecond.R
import kotlinx.android.synthetic.main.fragment_vehicle_details.*

class DetailsVehicleFragment: Fragment(R.layout.fragment_vehicle_details) {

    private val args: DetailsVehicleFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Glide
            .with(requireContext())
            .load(args.picture)
            .centerCrop()
            .error(R.drawable.ic_baseline_signal_off)
            .placeholder(R.drawable.ic_baseline_portrait)
            .into(detailsImageView)
        detailModelTextView.text = args.model
        detailSpeedTextView.text = args.speed
    }


}