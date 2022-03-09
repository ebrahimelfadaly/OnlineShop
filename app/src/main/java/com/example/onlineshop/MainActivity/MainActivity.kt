package com.example.onlineshop.MainActivity

import android.content.IntentFilter
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavAction
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.onlineshop.NavGraphDirections
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.databinding.ActivityMainBinding
import com.example.onlineshop.repository.RepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var networkChange: NetworkChange

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

        networkChange = NetworkChange(this)

        this.registerReceiver(networkChange, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        val wishListNotificationAdapter = WishListNotificationAdapter(findViewById(R.id.favourite))
        val cartIconAdapter = CartNotificationAdapter(findViewById(R.id.cartView))
        val viewModelFactory = ViewModelFactory(
            RepositoryImpl(
                RemoteDataSourceImpl(),
                RoomDataSourceImpl(RoomService.getInstance(this.application))
            ), this.application
        )
        val viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[MainActivityViewModel::class.java]
        viewModel.getAllWishList().observe(this, {
            wishListNotificationAdapter.updateView(it.size)
        })
        wishListNotificationAdapter.favouriteButton.setOnClickListener {

            val action = NavGraphDirections.actionGlobalAllWishListFragment()
            navController.navigate(action)
        }
        viewModel.getAllCartList().observe(this, {
            cartIconAdapter.updateView(it.size)
        })
        cartIconAdapter.favouriteButton.setOnClickListener {
            val action = NavGraphDirections.actionGlobalCartFragment2()
            navController.navigate(action)

        }

        searchIcon.setOnClickListener {
//            Toast.makeText(this,"search",Toast.LENGTH_LONG).show()
//            navHostFragment = fragment as NavHostFragment
//            val graphInflater = (navHostFragment as NavHostFragment).navController.navInflater
//            val navGraph = graphInflater.inflate(R.navigation.nav_graph)
//            navController = (navHostFragment as NavHostFragment).navController
//            navGraph.startDestination = R.id.shopSearchFragment
//            navController!!.graph = navGraph
            val action = NavGraphDirections.actionGlobalShopSearchFragment()
            navController.navigate(action)

        }

        navView.setupWithNavController(navController)
    }
        override fun onDestroy() {
            super.onDestroy()
            this.unregisterReceiver(networkChange)
        }

}