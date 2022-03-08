package com.example.onlineshop.data.itemPojo

import com.google.gson.annotations.SerializedName

data class ProductModelX(
    @SerializedName("product") var product: ArrayList<Product> = arrayListOf()
)
