package com.example.pranav.productbooking.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pranav.productbooking.ActBase
import com.example.pranav.productbooking.ActLogin
import com.example.pranav.productbooking.ActMain
import com.example.pranav.productbooking.helper.PrefKeys
import com.example.pranav.productbooking.model.User
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs

abstract class FragBase : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getRecourseLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpView()
    }

     fun getUserData(): User {

        return Gson().fromJson(Prefs.getString(PrefKeys.USER_DATA, ""), User::class.java)
    }

    fun getUserId(): String {
        return Prefs.getString(PrefKeys.USER_ID,"")
    }

    lateinit var baseContext: ActBase


    fun setBarTitle(title: String) {
        if (baseContext is ActMain) {
            (baseContext as? ActMain)?.setBarTitle(title)
        }
    }

    fun showBack(isShow: Boolean){
        if(baseContext is ActMain){
            (baseContext as? ActMain)?.showBack(isShow)
        }
    }

    fun showLoader(){
        if(baseContext is ActMain){
            (baseContext as? ActMain)?.showIroidLoader()
        }
        if(baseContext is ActLogin){
            (baseContext as? ActLogin)?.showIroidLoader()
        }
    }
    fun closeLoader(){
        if(baseContext is ActMain){
            (baseContext as? ActMain)?.closeIroidLoader()
        }
        if(baseContext is ActLogin){
            (baseContext as? ActLogin)?.closeIroidLoader()
        }
    }

    abstract fun setUpView()

    abstract fun getRecourseLayout(): Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.baseContext = (context as? ActBase?)!!
    }

}