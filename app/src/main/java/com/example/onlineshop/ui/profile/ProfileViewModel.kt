package com.example.onlineshop.ui.profile


import android.app.Application
import androidx.lifecycle.*
import com.example.onlineshop.data.entity.customer.Customer
import com.example.onlineshop.data.entity.customer.CustomerProfile
import com.example.onlineshop.data.entity.customer.CustomerX
import com.example.onlineshop.networkBase.SingleLiveEvent
import com.example.onlineshop.repository.IRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel(val repositoryImpl: IRepository, application: Application) : AndroidViewModel(application) {
    private val customerResponse= SingleLiveEvent<Customer>()
    private val postResult = SingleLiveEvent<CustomerX?>()
    fun getPostResult(): LiveData<CustomerX?> {
        return postResult
    }

    fun getCustomerInfo(): LiveData<Customer> {
        return customerResponse
    }

    fun getCustomer(id:String) {
        viewModelScope.launch {
            var customer = repositoryImpl.getCustomer(id)
            customer.let { customerResponse.postValue(customer?.customer) }
            Timber.i("enas customer"+customer)
        }
     }

    fun UpdateCustomers(id:String,item: CustomerProfile) {

        var customer = item.customer?.firstName?.let { Customer(it,"null", "null") }
        var customerx :CustomerX?= customer?.let { CustomerX(it) }

        val jop = viewModelScope.launch {
            customerx = repositoryImpl.updateCustomer(id,item)
        }
        jop.invokeOnCompletion {
            postResult.postValue(customerx)
            Timber.i("enas customer1+" + customerx)

        }
    }
}