package com.example.pranav.productbooking

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class ActSplash : ActBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)
        Handler().postDelayed({

            if (getUserId().isNotBlank())
                startActivity(Intent(this, ActMain::class.java))
            else
                startActivity(Intent(this, ActLogin::class.java))

            finish()

        }, 2000)
    }
}
