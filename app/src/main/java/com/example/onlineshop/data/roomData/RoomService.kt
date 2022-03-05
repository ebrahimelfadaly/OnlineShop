package com.example.onlineshop.data.roomData

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.onlineshop.data.itemPojo.OrderObject
import com.example.onlineshop.data.itemPojo.Product
import com.example.onlineshop.data.itemPojo.ProductCartModule

import com.example.onlineshop.data.roomData.cartBag.CartDao
import com.example.onlineshop.data.roomData.order.OrderDao
import com.example.onlineshop.data.roomData.wishBag.WishDao

@TypeConverters(Converter::class)

@Database(entities = [Product::class,ProductCartModule::class,OrderObject::class],version = 1,exportSchema = false)
abstract class RoomService : RoomDatabase() {
    companion object{
        @Volatile
        private var db : RoomService? =null

        fun getInstance(application: Application): RoomService? {
            synchronized(this) {
                if (db == null)
                    db = Room.databaseBuilder(
                        application, RoomService::class.java, "WishListDataBase"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return db
        }

    }


    abstract fun wishDao(): WishDao
    abstract fun caerDao(): CartDao
    abstract fun orderDao(): OrderDao

}