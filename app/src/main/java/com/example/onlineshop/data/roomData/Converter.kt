package com.example.onlineshop.data.roomData


import androidx.room.TypeConverter
import com.example.onlineshop.data.itemPojo.Image
import com.example.onlineshop.data.itemPojo.Images
import com.example.onlineshop.data.itemPojo.Options
import com.example.onlineshop.data.itemPojo.Variants

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromWetherToGesonx(list: List<Variants>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToWeatherx(gson: String): List<Variants> {
        val type = object : TypeToken<List<Variants>>() {}.type
        return Gson().fromJson(gson, type)
    }


    @TypeConverter
    fun fromOptionsToGesonx(list: List<Options>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonTOptions(gson: String): List<Options> {
        val type = object : TypeToken<List<Variants>>() {}.type
        return Gson().fromJson(gson, type)
    }


    @TypeConverter
    fun fromImagesToGesonx(list: List<Images>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonTOImages(gson: String): List<Images> {
        val type = object : TypeToken<List<Variants>>() {}.type
        return Gson().fromJson(gson, type)
    }


    @TypeConverter
    fun fromImageToGesonx(app: Image): String = Gson().toJson(app)

    @TypeConverter
    fun fromJsonTOImage(string: String): Image = Gson().fromJson(string, Image::class.java)


}