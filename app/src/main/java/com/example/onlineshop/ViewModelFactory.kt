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
import com.example.onlineshop.ui.meScreen.MeViewModel


class ViewModelFactory(private val repositoryImpl: IRepository,private val application: Application):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ShopViewModel::class.java) -> {
                ShopViewModel(repositoryImpl, application) as T
            }
            modelClass.isAssignableFrom(AllWishListViewModel::class.java) -> {
                AllWishListViewModel(repositoryImpl, application) as T
            }
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(repositoryImpl, application) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repositoryImpl, application) as T
            }
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> {
                CategoryViewModel(repositoryImpl, application) as T

            }
            modelClass.isAssignableFrom(MeViewModel::class.java)-> {
               MeViewModel(repositoryImpl,application) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
      }
    }



