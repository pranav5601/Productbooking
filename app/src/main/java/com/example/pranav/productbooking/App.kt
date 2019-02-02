package com.example.pranav.productbooking

import android.app.Application
import android.content.ContextWrapper
import com.google.firebase.database.FirebaseDatabase
import com.pixplicity.easyprefs.library.Prefs

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)


        Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(packageName)
                .setUseDefaultSharedPreference(true)
                .build()
    }
}