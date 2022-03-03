package com.example.onlineshop.data.itemPojo
import com.google.gson.annotations.SerializedName


data class ProductItem (
	@SerializedName("product") val product : Product
)