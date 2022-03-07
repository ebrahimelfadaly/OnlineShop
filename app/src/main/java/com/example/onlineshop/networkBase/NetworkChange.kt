package com.example.onlineshop.networkBase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import com.example.onlineshop.networkBase.NetworkUtils.NETWORK_STATUS_NOT_CONNECTED
import com.example.onlineshop.networkBase.NetworkUtils.getConnectivityStatusString
import com.example.onlineshop.R

class NetworkChange(context: Context): BroadcastReceiver() {
   private var networkStatus:Boolean

    init {
      val status=getConnectivityStatusString(context)
      networkStatus=status!=NETWORK_STATUS_NOT_CONNECTED
        isOnline=networkStatus
   }
    companion object{
        var isOnline=false

    }

    @SuppressLint("LogNotTimber", "ResourceAsColor")
    override fun onReceive(p0: Context?, p1: Intent?) {
        val status = getConnectivityStatusString(p0!!)
        if ("android.net.conn.CONNECTIVITY_CHANGE" == p1?.action) {
            val activity : Activity = p0 as Activity
            if (status == NETWORK_STATUS_NOT_CONNECTED){
                isOnline=false
                activity.findViewById<View>(R.id.viewInternetConnection).visibility= View.VISIBLE
            }else {
                isOnline=true
                activity.findViewById<View>(R.id.viewInternetConnection).visibility= View.GONE
            }

        }
    }
}