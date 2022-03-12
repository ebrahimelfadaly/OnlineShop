package com.example.onlineshop.ui.ShopTap

import android.app.Application
<<<<<<< Updated upstream
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.repository.IRepository
=======
import android.util.Log
import androidx.lifecycle.*
import com.example.onlineshop.data.entity.ads_discount_codes.AllCodes
import com.example.onlineshop.data.entity.allproducts.AllProducts
import com.example.onlineshop.data.entity.allproducts.allProduct
import com.example.onlineshop.data.entity.customProduct.Product
import com.example.onlineshop.data.entity.customProduct.ProductsList
import com.example.onlineshop.data.entity.smart_collection.Brands
import com.example.onlineshop.data.entity.smart_collection.SmartCollection
import com.example.onlineshop.data.itemPojo.ProductItem
import com.example.onlineshop.data.itemPojo.ProductsBrand.ProductsModel
import com.example.onlineshop.data.remoteDataSource.network.Network
import com.example.onlineshop.networkBase.SingleLiveEvent

import com.example.onlineshop.repository.IRepository
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch

class ShopViewModel(val repositoryImpl: IRepository, application: Application) :
    AndroidViewModel(application) {

    var intentTOProductDetails: MutableLiveData<SmartCollection> = MutableLiveData()
  var  intentTOProductBrand: SingleLiveEvent<allProduct> = SingleLiveEvent()
    private val productsMutable = MutableLiveData<ProductsModel>()
    val productsByBrand: LiveData<ProductsModel>get()  = productsMutable


    init {
        fetchallProductsList()
        fetchallDiscountCodeList()
        fetchOnSaleProductsList()
        fetchMenProductsList()
        fetchWomanProductsList()
        fetchKidsProductsList()
        fetchAllBrands()

    }

//    fun fetchAllProducts()=repositoryImpl.fetchAllProducts()
      fun fetchAllBrands():MutableLiveData<Brands>{
          return repositoryImpl.getAllBrands()
      }

    fun fetchWomanProductsList(): MutableLiveData<ProductsList>? {
        return repositoryImpl.getWomanProductsList()
    }
>>>>>>> Stashed changes

class ShopViewModel(val repositoryImpl: IRepository, application: Application) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
<<<<<<< Updated upstream
    val text: LiveData<String> = _text
}
=======

    fun fetchallDiscountCodeList(): MutableLiveData<AllCodes> {
        return repositoryImpl.getAllDiscountCodeList()
    }

   fun getProductBrand(brandId:String?)
   {
       viewModelScope.launch {
           val product=Network.apiService.getProductsByVendor(brandId!!)
           if (product.isSuccessful)
           {
               productsMutable.postValue(product.body())
               Log.i("TAG", "getProductBrand: ${product.body()}")
           }
           else
           {
               Log.i("productBrand", "getProductBrand:error ")
           }
       }
   }


}
>>>>>>> Stashed changes
