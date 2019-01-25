package com.example.pranav.productbooking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pranav.productbooking.fragments.FragLogin

class ActLogin : ActBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)
        supportFragmentManager.beginTransaction().add(R.id.fragLoginContainer,FragLogin(), FragLogin::class.simpleName).commit()
    }
}
