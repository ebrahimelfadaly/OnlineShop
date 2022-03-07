package com.example.onlineshop.MainActivity

import android.content.IntentFilter
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
      lateinit var networkChange: NetworkChange

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        networkChange= NetworkChange(this)
        this.registerReceiver(networkChange, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))

        navView.setupWithNavController(navController)
    }
}