package com.example.pranav.productbooking

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.pranav.productbooking.fragments.FragHome
import com.google.firebase.auth.FirebaseAuth
import com.pixplicity.easyprefs.library.Prefs
import com.swyft.app.helper.Utils
import kotlinx.android.synthetic.main.act_main.*

class ActMain : ActBase() {

    private var firebaseAuth: FirebaseAuth?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        firebaseAuth = FirebaseAuth.getInstance()
        initHomeFragment()
        initClick()

    }

    private fun initClick() {
        imgBack.setOnClickListener {
            onBackPressed()
        }
        imgLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout(){
        if(firebaseAuth?.currentUser != null){
            firebaseAuth?.signOut()
            Prefs.clear()
            startActivity(Intent(this@ActMain,ActLogin::class.java))
            finish()
        }
    }

    fun setBarTitle(title: String) {
        txtTitle.text = title
    }

    fun showBack(isShow: Boolean){
        if(isShow){
            imgBack.visibility = View.VISIBLE
        }else{
            imgBack.visibility = View.GONE
        }
    }

    private fun initHomeFragment() {
        Utils.initHomeFrag(supportFragmentManager,FragHome(),R.id.fragMainContainer)
    }

}
