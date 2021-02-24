package com.skillbox.androidview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_deeplink.*

class DeepLinkActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deeplink)
        setTextView()
    }

    private fun setTextView(){
        intent.data?.lastPathSegment?.let { name ->
            webTextView.text = name
        }
    }
}