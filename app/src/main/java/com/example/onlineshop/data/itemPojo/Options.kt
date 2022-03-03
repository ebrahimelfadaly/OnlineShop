package com.example.onlineshop.data.itemPojo

import com.google.gson.annotations.SerializedName


data class Options (

	@SerializedName("id") val id : Long?,
	@SerializedName("product_id") val product_id : Long?,
	@SerializedName("name") val name : String?,
	@SerializedName("position") val position : Int?,
	@SerializedName("values") var values : List<String>?
)