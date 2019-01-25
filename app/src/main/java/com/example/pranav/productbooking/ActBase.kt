package com.example.pranav.productbooking

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.pranav.productbooking.helper.IRoidLoader
import com.example.pranav.productbooking.helper.PrefKeys
import com.pixplicity.easyprefs.library.Prefs

open class ActBase: AppCompatActivity() {

    private var iRoidLoader: IRoidLoader? = null
    fun getUserId(): String {
        return Prefs.getString(PrefKeys.USER_ID, "")
    }
    private fun getActivity(): Activity {
        return this
    }
    fun showIroidLoader() {

        if (iRoidLoader == null)
            iRoidLoader = IRoidLoader(getActivity())

        iRoidLoader?.show()
    }
    fun closeIroidLoader() {
        iRoidLoader?.dismiss()
    }
}