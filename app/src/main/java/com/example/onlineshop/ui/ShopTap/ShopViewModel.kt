package com.example.onlineshop.ui.ShopTap

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop.data.entity.ads_discount_codes.AllCodes
import com.example.onlineshop.data.entity.allproducts.AllProducts
import com.example.onlineshop.data.entity.customProduct.Product
import com.example.onlineshop.data.entity.customProduct.ProductsList
import com.example.onlineshop.data.entity.smart_collection.Brands
import com.example.onlineshop.data.entity.smart_collection.SmartCollection
import com.example.onlineshop.data.itemPojo.ProductItem
import com.example.onlineshop.repository.IRepository

class ShopViewModel(val repositoryImpl: IRepository, application: Application) :
    AndroidViewModel(application) {

    var intentTOProductDetails: MutableLiveData<SmartCollection> = MutableLiveData()
  var  intentTOProductBrand: MutableLiveData<Product> = MutableLiveData()


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

    fun fetchMenProductsList(): MutableLiveData<ProductsList> {
        return repositoryImpl.getMenProductsList()
    }

    fun fetchOnSaleProductsList(): MutableLiveData<ProductsList> {
        return repositoryImpl.getOnSaleProductsList()
    }

    fun fetchKidsProductsList(): MutableLiveData<ProductsList> {
        return repositoryImpl.getKidsProductsList()
    }

    fun fetchallProductsList(): MutableLiveData<AllProducts> {
        return repositoryImpl.getAllProductsList()
    }

    fun fetchallDiscountCodeList(): MutableLiveData<AllCodes> {
        return repositoryImpl.getAllDiscountCodeList()
    }
    fun fetchGetProductB(id:Long): MutableLiveData<List<Product>>
    {
        return repositoryImpl.fetchCatProducts(id)
    }

}