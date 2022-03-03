package com.example.onlineshop.data.entity.discount


import com.example.onlineshop.data.entity.discount.DiscountCode
import com.google.gson.annotations.SerializedName

data class discountCodes(
    @SerializedName("discount_codes")
    val discountCodes: List<DiscountCode>?
)