package com.example.onlineshop.data.roomData.order

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.onlineshop.data.itemPojo.OrderObject


@Dao
interface OrderDao {
    @Query("SELECT * FROM OrderObject")
    fun getAllOrderList(): LiveData<List<OrderObject>>
}