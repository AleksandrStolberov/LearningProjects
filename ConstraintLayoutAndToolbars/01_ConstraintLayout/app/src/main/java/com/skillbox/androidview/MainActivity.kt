package com.skillbox.androidview

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.checkboxView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonEntry.setOnClickListener {
            setEnabled(false)
            val progress = ProgressBar(this).apply {
                ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                this.id = Random.nextInt()
            }
            consraintView.addView(progress)
            val set = ConstraintSet()
            set.clone(consraintView)
            set.connect(progress.id, ConstraintSet.TOP, buttonEntry.id, ConstraintSet.BOTTOM, 16)
            set.connect(progress.id, ConstraintSet.START, consraintView.id, ConstraintSet.START, 16)
            set.connect(progress.id, ConstraintSet.END, consraintView.id, ConstraintSet.END, 16)
            set.applyTo(consraintView)

            Handler().postDelayed({
                setEnabled(true)
                consraintView.removeView(progress)
                Toast.makeText(this, R.string.text_login, Toast.LENGTH_SHORT).show()
            }, 2000)
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
        loginGroup.referencedIds.forEach { id ->
            findViewById<View>(id).isEnabled = isEnabled
        }
    }
}
