package com.skillbox.permissionsanddate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_permission.*

class PermissionFragment : Fragment(R.layout.fragment_permission) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showLocationFragment()

        resolveButton.setOnClickListener {
            showPermissionLocation()
        }
    }

    private fun showPermissionLocation() {
        val isPermission = isPermissionLocation()
        if (isPermission) {
            showLocationFragment()
        } else {
            requestLocationPermission()
        }
    }

    private fun showLocationFragment() {
        val isPermission = isPermissionLocation()
        if (isPermission) {
            requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LocationFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }


    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun isPermissionLocation(): Boolean {
        val isPermissionLocation = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        permissionTextView.isVisible = !isPermissionLocation
        return isPermissionLocation
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it != PackageManager.PERMISSION_GRANTED }) {
            Toast.makeText(
                requireContext(),
                "Невозможно отбразить список локаций без разрешения",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            showPermissionLocation()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 5344
    }
}