package com.example.onlineshop.ui.ShopTap.shopTapcategory

import android.content.ClipData
import android.content.ClipboardManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.onlineshop.NavGraphDirections
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.entity.customProduct.Product
import com.example.onlineshop.data.entity.smart_collection.Brands
import com.example.onlineshop.data.entity.smart_collection.SmartCollection
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.repository.RepositoryImpl
import com.example.onlineshop.ui.ShopTap.ShopItemAdapter
import com.example.onlineshop.ui.ShopTap.ShopViewModel

import com.facebook.shimmer.ShimmerFrameLayout


import kotlinx.android.synthetic.main.fragment_woman_product.*

import kotlinx.coroutines.*



class WomanProductFragment : Fragment(){

//    private lateinit var  shopTabViewModel : ShopViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val application = requireNotNull(this.activity).application
//        val repository = RepositoryImpl(
//            RemoteDataSourceImpl(),
//            RoomDataSourceImpl(RoomService.getInstance(application))
//        )
//        val viewModelFactory = ViewModelFactory(repository, application)
//        shopTabViewModel =
//            ViewModelProvider(
//                this, viewModelFactory
//            )[ShopViewModel::class.java]
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_woman_product, container, false)
//    }
////
////    override fun onActivityCreated(savedInstanceState: Bundle?) {
////        super.onActivityCreated(savedInstanceState)
////
////        val shf1 = requireActivity().findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout1)
////        val shf2 = requireActivity().findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout2)
////
////        shf1.startShimmer()
////        shf2.startShimmer()
////
////        if (NetworkChange.isOnline) {
////            networkView.visibility = View.GONE
////            woman_linear.visibility = View.VISIBLE
////            shopTabViewModel.fetchAllBrands()?.observe(viewLifecycleOwner, {
////                if (it != null) {
////                    shimmerFrameLayout1.stopShimmer()
////                    shimmerFrameLayout2.stopShimmer()
////                    shimmerFrameLayout1.visibility = View.GONE
////                    shimmerFrameLayout2.visibility = View.GONE
////
////                    itemsRecView.visibility = View.VISIBLE
////
////                    bindWomanProductRecyclerView(
////                        it.smart_collections,shopTabViewModel.intentTOProductDetails
////
////                    )
////                }
////            })
////
////
////
////            shopTabViewModel.fetchallDiscountCodeList().observe(viewLifecycleOwner, {
////                val allCodes = it
////                if (allCodes != null) {
////                    play.setOnClickListener {
////                        play.visibility = View.GONE
////                        Glide.with(this)
////                            .load(R.drawable.woman_three)
////                            .into(ads)
////                        GlobalScope.launch(Dispatchers.Main) {
////                            delay(1500)
////                            lin.visibility = View.VISIBLE
////                            codeTextView.text = allCodes.discountCodes[3].code
////                        }
////                    }
////
////                }
////            })
////        }else{
////            networkView.visibility = View.VISIBLE
////            woman_linear.visibility = View.GONE
////        }
////
////        codeTextView.setOnClickListener {
////            val clipboard = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
////            clipboard?.setPrimaryClip(ClipData.newPlainText("",codeTextView.text))
////            Toast.makeText(requireContext(),"Copied", Toast.LENGTH_SHORT).show()
////
////        }
////
////    }
////
////
////    private fun bindWomanProductRecyclerView(
////        itemName: List<SmartCollection>,
////         intentTOProductDetails : MutableLiveData<SmartCollection>
////
////    ) {
////
////        itemsRecView.adapter = ShopItemAdapter(requireContext(), itemName,intentTOProductDetails,this)
////
////    }
////
////    override fun getItemProduct(smartCollection: SmartCollection, position: Int) {
////       shopTabViewModel.getProductBrand(smartCollection.title)
////      //  findNavController().navigate(R.id.products)
////
////
////    }
//
//
}