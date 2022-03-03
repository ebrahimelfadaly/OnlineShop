package com.example.onlineshop.data.entity.customProduct

import com.google.gson.annotations.SerializedName

data class Option(
    val id: Double,
    val name: String,
    val position: Double,
    @SerializedName("product_id")
    val productId: Double
)
