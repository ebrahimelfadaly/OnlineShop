package com.example.onlineshop.MainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.onlineshop.repository.IRepository


class MainActivityViewModel(val repositoryImpl: IRepository,application: Application):AndroidViewModel(application) {

    fun getAllWishList()= repositoryImpl.getAllWishList()

    fun getAllCartList() = repositoryImpl.getAllCartList()
}