package com.skillbox.permissionsanddate

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class PermissionApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}