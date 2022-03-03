package com.example.onlineshop.networkBase

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    private const val TYPE_MOBILE=2
    private const val TYPE_WIFI=1
    private const val TYPE_NOT_CONNECTED = 0
    const val NETWORK_STATUS_NOT_CONNECTED = 0
    private const val NETWORK_STATUS_WIFI = 1
    private const val NETWORK_STATUS_MOBILE = 2

    @Suppress("DEPRECATION")
  private fun getConnectivityStatus(context: Context):Int{
     val connect=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
     val activeNetwork=connect.activeNetworkInfo
       if(activeNetwork!=null){
           if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI
           if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE
        }
     return TYPE_NOT_CONNECTED
  }
    fun getConnectivityStatusString(context: Context): Int {
        val conn = getConnectivityStatus(context)
        var status = 0
        when (conn) {
            TYPE_WIFI -> {
                status = NETWORK_STATUS_WIFI
            }
            TYPE_MOBILE -> {
                status = NETWORK_STATUS_MOBILE
            }
            TYPE_NOT_CONNECTED -> {
                status = NETWORK_STATUS_NOT_CONNECTED
            }
        }
        return status
    }

}