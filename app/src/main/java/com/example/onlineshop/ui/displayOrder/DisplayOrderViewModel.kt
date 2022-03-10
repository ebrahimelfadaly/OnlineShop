package com.example.onlineshop.ui.displayOrder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.data.entity.orderGet.GetOrders
import com.example.onlineshop.repository.IRepository
import com.example.onlineshop.utils.FilterData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler

class DisplayOrderViewModel (val repositoryImpl: IRepository, application: Application) :
    AndroidViewModel(application) {
    lateinit var disposable: Disposable
    var orders: MutableLiveData<List<GetOrders.Order?>> = MutableLiveData()
    var payNowMutableData: MutableLiveData<GetOrders.Order> = MutableLiveData()
    var cancelMutableData: MutableLiveData<GetOrders.Order> = MutableLiveData()
    var error: MutableLiveData<Boolean> = MutableLiveData()
    //    var deleteOrder: MutableLiveData<Boolean> = MutableLiveData()
    var showOrderDetails: MutableLiveData<Long> = MutableLiveData()


    fun getPaidOrders( userId: Long) {
        disposable = repositoryImpl.getAllOrders().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({ vehicles ->
                orders.postValue(
                    FilterData.getPaidData(
                        FilterData.getAllData(vehicles.orders!!, userId)
                    )
                )

            }, { error ->
                this.error.postValue(true)
            })

    }

    fun getProductAllProuducts()= repositoryImpl.getAllProductsList()

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    fun getUnPaidOrders(userId: Long) {

        disposable = repositoryImpl.getAllOrders().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({ vehicles ->
                orders.postValue(
                    FilterData.getUnPaidData(
                        FilterData.getAllData(vehicles.orders!!, userId)
                    )
                )
            }, { error ->
                this.error.postValue(true)
            })
    }

    fun deleteOrder(order_id: Long){
        repositoryImpl.deleteOrder(order_id)
    }

    fun observeDeleteOrder(): MutableLiveData<Boolean> {
        return repositoryImpl.observeDeleteOrder()
    }
}
