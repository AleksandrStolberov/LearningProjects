package com.skillbox.fragment.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.fragment.R

class AppActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        if (savedInstanceState == null) {
            showSetPagesFragment()
        }
    }

    private fun showSetPagesFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.container_viewPager, ViewPagerFragment())
            .commit()
    }

}