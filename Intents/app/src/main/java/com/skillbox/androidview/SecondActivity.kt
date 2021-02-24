package com.skillbox.androidview

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Toast.makeText(this, R.string.text_login, Toast.LENGTH_SHORT).show()
        buttonPhone.setOnClickListener {
            phoneIntent()
        }

    }


    private fun phoneIntent() {
        val isPermissionCall = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
        val numberPhone = editPhone.text.toString()
        val isValidPhone = Patterns.PHONE.matcher(numberPhone).matches()
        if (!isValidPhone) {
            Toast.makeText(this, "Неверный формат", Toast.LENGTH_SHORT).show()
        } else {
            if (!isPermissionCall) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                    33
                )
            } else {
                resultCallTextView.text = ""
                val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel: $numberPhone")
                    putExtra(Intent.ACTION_DIAL, numberPhone)
                }
                phoneIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(phoneIntent, REQUEST_DIAL_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_DIAL_CODE && resultCode == Activity.RESULT_OK && data != null) {
            resultCallTextView.text = "Звонок закончен"
            } else {
                resultCallTextView.text = "Звонок отменен"
            }
            super.onActivityResult(requestCode, resultCode, data)
        }

    companion object {
        private const val REQUEST_DIAL_CODE = 545
    }
}

