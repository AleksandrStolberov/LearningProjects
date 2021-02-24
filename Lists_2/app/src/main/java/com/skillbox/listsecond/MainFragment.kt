package com.skillbox.listsecond

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.skillbox.listsecond.recycler.VehicleGridFragment
import com.skillbox.listsecond.recycler.VehicleLinearFragment
import com.skillbox.listsecond.recycler.VehicleStaggeredFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        linearButton.setOnClickListener {
            showLinearLayoutList()
        }

        gridButton.setOnClickListener {
            showGridLayoutList()
        }

        staggeredButton.setOnClickListener {
            showStaggeredGridLayoutList()
        }
    }

    private fun showLinearLayoutList() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                VehicleLinearFragment()
            )
            .addToBackStack(null)
            .commit()
    }

    private fun showGridLayoutList() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                VehicleGridFragment()
            )
            .addToBackStack(null)
            .commit()
    }

    private fun showStaggeredGridLayoutList() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                VehicleStaggeredFragment()
            )
            .addToBackStack(null)
            .commit()
    }

}