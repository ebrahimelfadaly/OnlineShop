package com.example.onlineshop.data.remoteDataSource


import androidx.lifecycle.MutableLiveData
import com.example.onlineshop.data.entity.ads_discount_codes.AllCodes
import com.example.onlineshop.data.entity.allproducts.AllProducts
import com.example.onlineshop.data.entity.customProduct.Product
import com.example.onlineshop.data.entity.customProduct.ProductsList
import com.example.onlineshop.data.entity.customer.*
import com.example.onlineshop.data.entity.order.Orders
import com.example.onlineshop.data.entity.orderGet.GetOrders
import com.example.onlineshop.data.entity.orderGet.OneOrderResponce
import com.example.onlineshop.data.entity.smart_collection.Brands
import com.example.onlineshop.networkBase.SingleLiveEvent
import io.reactivex.Observable

interface RemoteDataIN {
    //shopTab
    fun getWomanProductsList() : MutableLiveData<ProductsList>
    fun getKidsProductsList() : MutableLiveData<ProductsList>
    fun getMenProductsList() : MutableLiveData<ProductsList>
    fun getOnSaleProductsList() : MutableLiveData<ProductsList>
    fun getAllProductsList() : MutableLiveData<AllProducts>
    fun getAllDiscountCodeList() : MutableLiveData<AllCodes>
    fun getAllBrands() :MutableLiveData<Brands>
    fun getProuduct(id : Long)

    fun fetchCatProducts(colID:Long): MutableLiveData<List<Product>>
    fun fetchAllProducts(): MutableLiveData<List<com.example.onlineshop.data.itemPojo.Product>>

    fun getAllOrders() : Observable<GetOrders>

    fun deleteOrder(order_id : Long)
    fun observeDeleteOrder(): MutableLiveData<Boolean>

    fun getOneOrders(id: Long) : Observable<OneOrderResponce>

    //customer and orders

    suspend fun fetchCustomersData(): List<Customer>?
    suspend fun createCustomers(customer: CustomerX): CustomerX?
    suspend fun createCustomerAddress(id: String, customerAddress: CreateAddress): CreateAddressX?
    suspend fun getCustomerAddresses(id: String): List<Addresse>?
    suspend fun updateCustomerAddresses(
        id: String,
        addressID: String,
        customerAddress: UpdateAddresse
    ): CreateAddressX?
    suspend fun getCustomerAddress(id: String, addressID: String): CreateAddressX?

    suspend fun delCustomerAddresses(id: String, addressID: String)
    suspend fun setDafaultCustomerAddress(id: String, addressID: String): CreateAddressX?

    suspend fun getCustomer( id: String): CustomerX?

    suspend fun updateCustomer(id: String, customer: CustomerProfile): CustomerX?
    fun createOrder(order: Orders)
    fun getCreateOrderResponse(): SingleLiveEvent<OneOrderResponce?>


}