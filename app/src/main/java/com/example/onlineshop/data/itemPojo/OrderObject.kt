package com.example.onlineshop.data.itemPojo


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class OrderObject(
    @PrimaryKey
    @SerializedName("id") val id : Long,
    @SerializedName("title") val title : String?,
    @SerializedName("price") val price : Double?,
    @SerializedName("src") val src : String?,
    @SerializedName("item_number") val item_number : String?)
