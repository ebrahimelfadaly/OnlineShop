package com.example.onlineshop.ui.ShowOneOrderDetails

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.data.entity.orderGet.GetOrders
import com.example.onlineshop.data.entity.orderGet.OneOrderResponce
import com.example.onlineshop.repository.IRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler

class ShowOneOrderDetailsVM  (val repositoryImpl: IRepository, application: Application) :
    AndroidViewModel(application) {
    private val orderLiveData : MutableLiveData<OneOrderResponce> = MutableLiveData()
    var payNowMutableData: MutableLiveData<GetOrders.Order> = MutableLiveData()
    var cancelMutableData: MutableLiveData<GetOrders.Order> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getOneOrders(id: Long): LiveData<OneOrderResponce> {
        repositoryImpl.getOneOrders(id).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({ order ->
                orderLiveData.postValue(order)
            }, { error ->

            })
        return orderLiveData
    }


    fun getProductAllProuducts()= repositoryImpl.getAllProductsList()

    fun deleteOrder(order_id: Long){
        repositoryImpl.deleteOrder(order_id)
    }
    fun observeDeleteOrder(): MutableLiveData<Boolean> {
        return repositoryImpl.observeDeleteOrder()
    }
}