package com.example.onlineshop.ui.Payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlineshop.R
import kotlinx.android.synthetic.main.activity_stripe.*

class StripeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stripe)
        pay.setOnClickListener {
            startActivity(
                Intent(this, CheckoutActivity::class.java)

            )
        }
    }
}