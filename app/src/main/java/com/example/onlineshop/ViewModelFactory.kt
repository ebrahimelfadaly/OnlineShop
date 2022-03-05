package com.example.onlineshop

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.repository.IRepository
import com.example.onlineshop.ui.ShopTap.ShopViewModel


class ViewModelFactory(private val repositoryImpl: IRepository,private val application: Application):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            return ShopViewModel(repositoryImpl, application) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

    }

}