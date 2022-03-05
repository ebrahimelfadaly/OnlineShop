package com.example.onlineshop.ui.entry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.onlineshop.MainActivity.MainActivity
import com.example.onlineshop.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val splashTimeOut = 3000
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, splashTimeOut.toLong())
    }
}