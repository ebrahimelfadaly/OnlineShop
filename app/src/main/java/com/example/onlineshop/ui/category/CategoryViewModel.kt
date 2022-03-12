package com.example.onlineshop.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.repository.IRepository

class CategoryViewModel(val repositoryImpl: IRepository, application: Application) :AndroidViewModel(application) {

    fun fetchCatProducts(colID:Long)= repositoryImpl.fetchCatProducts(colID)
    fun fetchAllProducts()=repositoryImpl.fetchAllProducts()
}