package com.example.pranav.productbooking.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pranav.productbooking.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.swyft.app.helper.Utils
import kotlinx.android.synthetic.main.frag_create_user.*
import android.widget.Toast
import com.example.pranav.productbooking.helper.Constant
import com.example.pranav.productbooking.model.User
import com.google.firebase.database.ServerValue
import java.util.HashMap




class FragCreateUser : FragBase() {

    private var mAuth: FirebaseAuth? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var mUserReference: DatabaseReference? = null

    override fun setUpView() {
        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        mUserReference = firebaseDatabase?.reference
        setBarTitle("Create User")
        showBack(true)
        initClick()
    }

    private fun initClick() {
        btnSignUp.setOnClickListener {

            if (validData()) {
                showLoader()
                storeData()
            }

        }

    }

    private fun storeData() {

        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()

        mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(baseContext) { task ->
                    if (task.isSuccessful) {

                        Log.e("uid",mAuth?.uid)

                        mUserReference = firebaseDatabase?.reference?.child(Constant.USER)
                                ?.child(mAuth?.uid)
                            val user =  User()
                        user.user_name = edtFullName.text.toString()
                        user.email_id = edtEmail.text.toString()
                        user.password = edtPassword.text.toString()
                        user.address_1 = edtAddress1.text.toString()
                        user.address_2 = edtAddress2.text.toString()
                        user.city = edtCity.text.toString()
                        user.mobile = edtMobile.text.toString()
                        user.status = "1"

                        mUserReference?.setValue(user)?.addOnCompleteListener(baseContext){
                            task -> if(task.isSuccessful){
                            clearData()
                            Utils.showToast(baseContext,"Account created")
                            edtFullName.requestFocus()
                            closeLoader()

                        } else{
                            closeLoader()
                            Utils.showToast(baseContext,task.exception?.message)
                        }
                        }

                    } else {
                        closeLoader()
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, task.exception?.message,
                                Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }

    }

    private fun clearData() {
        edtFullName.setText("")
        edtEmail.setText("")
        edtPassword.setText("")
        edtAddress1.setText("")
        edtAddress2.setText("")
        edtCity.setText("")
        edtMobile.setText("")

    }

    override fun getRecourseLayout(): Int = R.layout.frag_create_user


    private fun validData(): Boolean {
        if (!Utils.isStringValid(edtFullName.text.toString())) {
            Utils.yoyoAnimate(edtFullName)
            return false
        }
        if (!Utils.isStringValid(edtEmail.text.toString())) {
            Utils.yoyoAnimate(edtEmail)
            return false
        }
        if (!Utils.isStringValid(edtPassword.text.toString())) {
            Utils.yoyoAnimate(edtPassword)
            return false
        }
        if (!Utils.isStringValid(edtAddress1.text.toString())) {
            Utils.yoyoAnimate(edtAddress1)
            return false
        }
        if (!Utils.isStringValid(edtCity.text.toString())) {
            Utils.yoyoAnimate(edtCity)
            return false
        }
        if (!Utils.isStringValid(edtMobile.text.toString())) {
            Utils.yoyoAnimate(edtMobile)
            return false
        }

        return true
    }
}
