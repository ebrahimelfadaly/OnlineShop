package com.example.onlineshop.ui.AllWishList

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.data.itemPojo.Product
import com.example.onlineshop.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class AllWishListViewModel(val repositoryImpl:IRepository ,application: Application):AndroidViewModel(application) {
    var intentTOProductDetails : MutableLiveData<Product> = MutableLiveData()
    var deleteItem : MutableLiveData<Product> = MutableLiveData()
    lateinit var deleteOneItemFromWish : Job
     fun getAllWishList()=repositoryImpl.getAllWishList()
    fun deleteOneItemFromWishList(id : Long) {
        deleteOneItemFromWish = CoroutineScope(Dispatchers.IO).launch {
            repositoryImpl.deleteOneWishItem(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (deleteOneItemFromWish != null && !deleteOneItemFromWish.isCancelled) {
            deleteOneItemFromWish.cancel()
        }
    }
}