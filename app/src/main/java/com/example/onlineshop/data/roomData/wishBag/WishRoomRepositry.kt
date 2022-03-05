package com.example.onlineshop.data.roomData.wishBag

import android.app.Application
import com.example.onlineshop.data.itemPojo.Product

import com.example.onlineshop.data.roomData.RoomService


class WishRoomRepositry(application: Application) {
    val database : RoomService?= RoomService.getInstance(application)
    val wishDao : WishDao = database!!.wishDao()

    fun getFourWishList() = wishDao.getFourFromWishList()
    fun getAllWishList() = wishDao.getAllWishList()

    suspend fun saveWishList(wishItem: Product) = wishDao.saveWishList(wishItem)
    suspend fun deleteOneWishItem(id: Long) = wishDao.deleteOneWithItem(id)

    fun getOneWithItem(id: Long) = wishDao.getOneWithItem(id)


}