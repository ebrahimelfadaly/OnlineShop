package com.example.onlineshop

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.MainActivity.MainActivityViewModel
import com.example.onlineshop.repository.IRepository
import com.example.onlineshop.ui.AllWishList.AllWishListViewModel
import com.example.onlineshop.ui.ShopTap.ShopViewModel
import com.example.onlineshop.ui.category.CategoryViewModel
import com.example.onlineshop.ui.login_register.ui.login.LoginViewModel


class ViewModelFactory(private val repositoryImpl: IRepository,private val application: Application):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            return ShopViewModel(repositoryImpl, application) as T
        }

        else if (modelClass.isAssignableFrom(AllWishListViewModel::class.java)) {
            return AllWishListViewModel(repositoryImpl,application) as T
        }
        else if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(repositoryImpl,application) as T

        else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repositoryImpl,application) as T
        }
        else if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(repositoryImpl,application) as T

        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

    }

}