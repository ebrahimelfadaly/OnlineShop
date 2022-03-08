package com.example.onlineshop.data.itemPojo.ProductsBrand


import com.example.onlineshop.data.itemPojo.Product
import com.google.gson.annotations.SerializedName

data class ProductsModel (

  @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf()

)