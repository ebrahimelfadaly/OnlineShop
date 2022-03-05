package com.example.shopy.datalayer.localdatabase.room.order

import android.app.Application
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.data.roomData.order.OrderDao

class OrderRepository(application: Application) {


    val database : RoomService?= RoomService.getInstance(application)
    val orderDao : OrderDao = database!!.orderDao()

    fun getAllOrdersList() = orderDao.getAllOrderList()
}