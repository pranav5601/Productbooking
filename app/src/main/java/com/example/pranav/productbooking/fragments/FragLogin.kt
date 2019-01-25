package com.example.pranav.productbooking.fragments

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.pranav.productbooking.ActMain

import com.example.pranav.productbooking.R
import com.example.pranav.productbooking.helper.Debug
import com.example.pranav.productbooking.helper.PrefKeys
import com.example.pranav.productbooking.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pixplicity.easyprefs.library.Prefs
import com.swyft.app.helper.Utils
import kotlinx.android.synthetic.main.frag_login.*

import java.util.Objects
import com.google.gson.Gson




class FragLogin : FragBase(){
    private val userRef = FirebaseDatabase.getInstance().reference.child("User")

    override fun getRecourseLayout(): Int = R.layout.frag_login


    private var mAuth: FirebaseAuth? = null




    override fun setUpView() {
        fillData()
        initClick()
    }
    private fun fillData() {
        mAuth = FirebaseAuth.getInstance()

    }
    private fun initClick() {

        btnLogin.setOnClickListener {
            showLoader()
            takeIf { validateData() }?.let {
                useLogin(edtEmailId.text.toString(), edtPassword.text.toString())

            }
        }

    }


    private fun useLogin(email: String, password: String) {
        mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(baseContext) { task ->
                    if (task.isSuccessful) {

                        val user = mAuth?.currentUser

                        if (user != null) {
                            val userId = user.uid
                            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(databaseError: DatabaseError?) {
                                    closeLoader()
                                }

                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.child(userId).exists()) {
                                        if (!userId.isEmpty()) {
                                            val usersData = dataSnapshot.child(userId).getValue(User::class.java)
                                            val gson = Gson()
                                            val userData= gson.toJson(usersData)
                                            Prefs.putString(PrefKeys.USER_DATA,userData)
                                            Log.e("USER_DATA",userData)
                                            Prefs.putString(PrefKeys.USER_ID, userId)
                                            Debug.e("login",user.uid)
                                            closeLoader()
                                            activity?.startActivity(Intent(activity, ActMain::class.java))
                                            activity?.finish()

                                    }
                                }

                                }
                            })

                        }

                    } else {
                        Toast.makeText(context, Objects.requireNonNull<Exception>(task.exception).message, Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }

    private fun validateData(): Boolean {

        if (!Utils.isStringValid(edtEmailId.text.toString())) {
            Utils.yoyoAnimate(edtEmailId)
            return false
        }

        if (!Utils.isStringValid(edtPassword.text.toString())) {
            Utils.yoyoAnimate(edtPassword)
            return false
        }
        return true

    }
}
