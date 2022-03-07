package com.example.onlineshop.data.roomData.cartBag

import android.app.Application
import com.example.onlineshop.data.itemPojo.ProductCartModule
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.data.roomData.cartBag.CartDao



class CartRoomRepository(application: Application) {

    val database : RoomService?= RoomService.getInstance(application)
    val cartDao : CartDao = database!!.caerDao()
    fun getAllCartList() = cartDao.getAllCartList()
    suspend fun saveCartList(cartItem: ProductCartModule) = cartDao.saveCartList(cartItem)
    suspend fun deleteOneCartItem(id: Long) = cartDao.deleteOnCartItem(id)

}