package com.example.onlineshop.data.entity.priceRules


import com.google.gson.annotations.SerializedName

data class PrerequisiteToEntitlementPurchase(
    @SerializedName("prerequisite_amount")
    val prerequisiteAmount: Any?
)