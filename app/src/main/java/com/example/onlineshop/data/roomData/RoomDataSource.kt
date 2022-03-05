package com.example.onlineshop.data.roomData

import androidx.lifecycle.LiveData
import com.example.onlineshop.data.itemPojo.OrderObject
import com.example.onlineshop.data.itemPojo.Product
import com.example.onlineshop.data.itemPojo.ProductCartModule


interface RoomDataSource {

    fun getAllCartList(): LiveData<List<ProductCartModule>>
    suspend fun saveCartList(withItem: ProductCartModule)
    suspend fun deleteOnCartItem(id: Long)
    fun getAllOrderList(): LiveData<List<OrderObject>>
    fun getFourFromWishList(): LiveData<List<Product>>
    fun getAllWishList(): LiveData<List<Product>>
    suspend fun saveWishList(withItem: Product)
    suspend fun deleteOneWithItem(id: Long)
    fun getOneWithItem(id: Long) : LiveData<Product>
    suspend fun deleteAllFromCart()
    fun saveAllCartList(dataList :List<ProductCartModule>)
    suspend fun deleteAllFromWishlist()
    fun getFourWishList(): LiveData<List<Product>>
}