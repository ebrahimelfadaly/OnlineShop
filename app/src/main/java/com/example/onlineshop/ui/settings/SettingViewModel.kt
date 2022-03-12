package com.example.onlineshop.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.onlineshop.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(val repositoryImpl: IRepository, application: Application):AndroidViewModel(application) {
    fun clearRoom(){
   CoroutineScope(Dispatchers.IO).launch {
       repositoryImpl.deleteAllFromCart()
       repositoryImpl.deleteAllFromWish()

   }



    }
}