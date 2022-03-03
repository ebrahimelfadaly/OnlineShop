package com.example.onlineshop.data.entity.allproducts


import com.google.gson.annotations.SerializedName

data class PresentmentPrice(
    @SerializedName("compare_at_price")
    val compareAtPrice: Any,
    val price: Price
)