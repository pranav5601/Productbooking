package com.example.pranav.productbooking.fragments

import android.view.View
import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.helper.PrefKeys
import com.google.firebase.database.FirebaseDatabase
import com.pixplicity.easyprefs.library.Prefs
import com.swyft.app.helper.Utils
import kotlinx.android.synthetic.main.frag_home.*
import kotlin.reflect.jvm.internal.impl.load.java.Constant


class FragHome : FragBase() {



    override fun getRecourseLayout(): Int = R.layout.frag_home


    override fun setUpView() {
        initClick()
        showBack(false)
        setBarTitle(getString(R.string.app_name))
        hideCreateProfile()
        getDataFromUid()


    }

    private fun getDataFromUid() {

        val uid: String = Prefs.getString(PrefKeys.USER_ID,"")



    }

    private fun hideCreateProfile() {
        if(getUserData().status == "1"){
            btnCreateProfile.visibility = View.GONE
            btnProfileList.visibility = View.GONE
        }
    }


    private fun initClick() {
        btnCreateProfile.setOnClickListener {
            Utils.replaceFragmentToActivity(baseContext.supportFragmentManager,FragCreateUser(),R.id.fragMainContainer)
        }
        btnCreateItems.setOnClickListener {
            Utils.replaceFragmentToActivity(baseContext.supportFragmentManager,FragCreateItems(),R.id.fragMainContainer)
        }
        btnShowList.setOnClickListener {
            Utils.replaceFragmentToActivity(baseContext.supportFragmentManager,FragDisplayItems(),R.id.fragMainContainer)

        }

    }


}
