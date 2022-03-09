package com.example.onlineshop.ui.meScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.data.itemPojo.Product
import com.example.onlineshop.repository.IRepository
import com.example.onlineshop.utils.FilterData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MeViewModel(val repositoryImpl: IRepository, application: Application): AndroidViewModel(application) {

    var intentTOProductDetails : MutableLiveData<Product> = MutableLiveData()
    var deleteItem : MutableLiveData<Product> = MutableLiveData()
    var paidOrders: MutableLiveData<Int> = MutableLiveData()
    var unPaidOrders: MutableLiveData<Int> = MutableLiveData()
    var error: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var disposablePaidOrder: Disposable
    lateinit var disposableUnPaidOrder: Disposable
    lateinit var deleteOneItemFromWish: Job


    fun getFourWishList() = repositoryImpl.getFourWishList()

    fun deleteOneItemFromWishList(id : Long) {
        deleteOneItemFromWish = CoroutineScope(Dispatchers.IO).launch {
            repositoryImpl.deleteOneWishItem(id)
        }
    }




    fun getPaidOrders( userId: Long) {
        disposablePaidOrder = repositoryImpl.getAllOrders().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({ vehicles ->
                paidOrders.postValue(
                    FilterData.getPaidData(
                        FilterData.getAllData(vehicles.orders!!, userId)
                    ).size
                )

            }, { error ->
                this.error.postValue(true)
            })

    }

    override fun onCleared() {
//        super.onCleared()
//        if (!disposablePaidOrder.isDisposed) {
//            disposablePaidOrder.dispose()
//        }
//        if (!disposableUnPaidOrder.isDisposed) {
//            disposableUnPaidOrder.dispose()
//        }
//        deleteOneItemFromWish.cancel()
    }

    fun getUnPaidOrders(userId: Long) {
        disposableUnPaidOrder = repositoryImpl.getAllOrders().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({ vehicles ->
                unPaidOrders.postValue(
                    FilterData.getUnPaidData(
                        FilterData.getAllData(vehicles.orders!!, userId)
                    ).size
                )
            }, { error ->
                this.error.postValue(true)
            })
    }


    /*private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text*/
}