package com.bikcode.firebaseappinitialcourse

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        //Enable offline mode
        Firebase.database.setPersistenceEnabled(true)
    }
}