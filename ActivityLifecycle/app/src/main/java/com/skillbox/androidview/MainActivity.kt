package com.skillbox.androidview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.checkboxView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    var textState: FromState = FromState(message = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DebugLogger.d(tag, "onCreate")

        if (savedInstanceState != null){
            textState =  savedInstanceState.getParcelable<FromState>(KEY_TEXT) ?: error("Ошибка")
        }

        getTexView()

        buttonEntry.setOnClickListener {
            if (editPassword.text.length < 7) {
                textState = textState.getText("Ошибка, пароль должен быть от 7 символов")
                getTexView()
            } else {
                textError.text = ""
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
                set.connect(progress.id, ConstraintSet.TOP, longOperationButton.id, ConstraintSet.BOTTOM, 16)
                set.connect(progress.id, ConstraintSet.START, consraintView.id, ConstraintSet.START, 16)
                set.connect(progress.id, ConstraintSet.END, consraintView.id, ConstraintSet.END, 16)
                set.applyTo(consraintView)

                Handler().postDelayed({
                    setEnabled(true)
                    consraintView.removeView(progress)
                    Toast.makeText(this, R.string.text_login, Toast.LENGTH_SHORT).show()
                }, 2000)
            }
        }

        longOperationButton.setOnClickListener {
            while (textView.text != "0") {
                textView.text = ""
            }
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


    override fun onStart() {
        DebugLogger.d(tag, "onStart")
        DebugLogger.i(tag, "onStart")
        DebugLogger.e(tag, "onStart")
        DebugLogger.v(tag, "onStart")

        super.onStart()
    }

    override fun onResume() {
        DebugLogger.d(tag, "onResume")
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_TEXT, textState)
    }

    companion object {
        private const val KEY_TEXT = "message"
    }

    override fun onPause() {
        DebugLogger.d(tag, "onPause")
        super.onPause()
    }

    override fun onStop() {
        DebugLogger.d(tag, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        DebugLogger.d(tag, "onDestroy")
        super.onDestroy()
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

    private fun getTexView(){
        textError.text = textState.message
    }
}

object DebugLogger {
    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    fun v(tag: String, message: String) {
        Log.v(tag, message)
    }
}