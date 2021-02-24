package com.skillbox.listfirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            showVehicleListFragment()
        }
    }

    private fun showVehicleListFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.container, VehicleListFragment())
            .commit()
    }
}