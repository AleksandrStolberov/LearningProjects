package com.skillbox.androidview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonEntry.setOnClickListener {
            setEnabled(false)
            val progress = ProgressBar(this).apply {
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).gravity = Gravity.CENTER_HORIZONTAL
            }
            Handler().postDelayed({
                setEnabled(true)
                linearText.removeView(progress)
                Toast.makeText(this, R.string.text_login, Toast.LENGTH_SHORT).show()
            }, 2000)
            linearText.addView(progress)
        }

        editEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editEmail.apply { active() }
            }

        })

        editPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editPassword.apply { active() }
            }
        })

        checkboxView.setOnCheckedChangeListener { buttonView, isChecked -> checkboxView.apply { active() } }

    }

    private fun active() {
        buttonEntry.isEnabled =
            !(editEmail.text.isEmpty() || editPassword.text.isEmpty() || !checkboxView.isChecked)
    }

    private fun setEnabled(isEnabled: Boolean) {
        editEmail.isEnabled = isEnabled
        editPassword.isEnabled = isEnabled
        checkboxView.isEnabled = isEnabled
        buttonEntry.isEnabled = isEnabled
    }
}

