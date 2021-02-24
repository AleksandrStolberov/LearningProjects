package com.skillbox.androidview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.checkboxView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"

    var textStatePassword: FromState = FromState(message = "")
    var textStateEmail: FromState = FromState(message = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DebugLogger.d(tag, "onCreate")

        if (savedInstanceState != null){
            textStatePassword = savedInstanceState.getParcelable<FromState>(KEY_TEXT) ?: error("Ошибка")
            textStateEmail = savedInstanceState.getParcelable<FromState>(KEY_EMAIL) ?: error("Ошибка")
        }

        val qweety = "dsfd"

        getTexView()

        buttonEntry.setOnClickListener {
            setLogin()
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

    private fun setLogin(){
        val isEmailValid =
            Patterns.EMAIL_ADDRESS.matcher(editEmail.text.toString()).matches()
        if (editPassword.text.length < 7) {
            textStatePassword =
                textStatePassword.getText("Ошибка, пароль должен быть от 7 символов")
            getTexView()
        }
         else if (!isEmailValid) {
                textStateEmail = textStateEmail.getText("Ошибка, неверный формат email")
                getTexView()
            } else {
                textError.text = ""
                textErrorEmail.text = ""
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
                    val secondIntent = Intent(this, SecondActivity::class.java)
                    startActivity(secondIntent)
                    finish()
                }, 2000)
            }
        }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_TEXT, textStatePassword)
        outState.putParcelable(KEY_EMAIL, textStateEmail)
    }

    companion object {
        private const val KEY_TEXT = "message"
        private const val KEY_EMAIL = "email"

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
        textError.text = textStatePassword.message
        textErrorEmail.text = textStateEmail.message
    }

    override fun onStart() {
        DebugLogger.d(tag, "onStart")
        super.onStart()
    }

    override fun onResume() {
        DebugLogger.d(tag, "onResume")
        super.onResume()
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

}

object DebugLogger {
    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }
}