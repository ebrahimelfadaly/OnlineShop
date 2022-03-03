package com.example.onlineshop.data.entity.priceRules


import com.example.onlineshop.data.entity.priceRules.PriceRule
import com.google.gson.annotations.SerializedName

data class priceRules(
    @SerializedName("price_rules")
    val priceRules: List<PriceRule>?
)