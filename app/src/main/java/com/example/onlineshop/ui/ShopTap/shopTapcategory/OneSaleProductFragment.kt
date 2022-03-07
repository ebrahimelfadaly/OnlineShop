package com.example.onlineshop.ui.ShopTap.shopTapcategory

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
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
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.entity.customProduct.Product
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.repository.RepositoryImpl
import com.example.onlineshop.ui.ShopTap.ShopItemAdapter
import com.example.onlineshop.ui.ShopTap.ShopViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.fragment_one_sale_product.*
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class OneSaleProductFragment : Fragment() {

//    private lateinit var  shopTabViewModel : ShopViewModel
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
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_one_sale_product, container, false)
//    }
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//        val shf1 = requireActivity().findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout1)
//        val shf2 = requireActivity().findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout2)
//
//        shf1.startShimmer()
//        shf2.startShimmer()
//
//        if (NetworkChange.isOnline) {
//            networkSaleView.visibility = View.GONE
//            Sale_lin.visibility = View.VISIBLE
//            shopTabViewModel.fetchOnSaleProductsList().observe(viewLifecycleOwner, {
//                Log.i("output", "$it******************")
//                if (it != null) {
//                    shimmerFrameLayout1.stopShimmer()
//                    shimmerFrameLayout2.stopShimmer()
//                    shimmerFrameLayout1.visibility = View.GONE
//                    shimmerFrameLayout2.visibility = View.GONE
//
//                    itemsRecView.visibility = View.VISIBLE
//                    bindWomanProductRecyclerView(
//                        it.products,
//                        shopTabViewModel.intentTOProductDetails
//                    )
//                }
//                Log.i("output", it.products[0].toString())
//
//            })
//
//            shopTabViewModel.intentTOProductDetails.observe(requireActivity(), {
//                if (NetworkChange.isOnline){
//
//                    shopTabViewModel.intentTOProductDetails = MutableLiveData()
////                    val action = NavGraphDirections.actionGlobalProuductDetailsFragment(it.id.toLong())
////                    findNavController().navigate(action)
//                }else{
//                    Toast.makeText(requireContext(),requireContext().resources.getString(R.string.no_internet_connection),
//                        Toast.LENGTH_SHORT).show()
//                }
//            })
//
//            shopTabViewModel.fetchallDiscountCodeList().observe(viewLifecycleOwner, {
//                val allCodes = it
//                if (allCodes != null) {
//                    startbtn.setOnClickListener {
//                        startbtn.visibility = View.GONE
//                        Glide.with(this)
//                            .load(R.drawable.sale_gif)
//                            .into(ads)
//                        GlobalScope.launch(Dispatchers.Main) {
//                            delay(1500)
//                            lin.visibility = View.VISIBLE
//                            codeTextView.text = allCodes.discountCodes[0].code
//                        }
//                    }
//
//                }
//            })
//        }else{
//            networkSaleView.visibility = View.VISIBLE
//            Sale_lin.visibility = View.GONE
//        }
//        codeTextView.setOnClickListener {
//            val clipboard = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
//            clipboard?.setPrimaryClip(ClipData.newPlainText("",codeTextView.text))
//            Toast.makeText(requireContext(),"Copied", Toast.LENGTH_SHORT).show()
//
//        }
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        shopTabViewModel.intentTOProductDetails = MutableLiveData()
//    }
//
//    private fun bindWomanProductRecyclerView(
//        itemName: List<Product>,
//        intentTOProductDetails: MutableLiveData<Product>
//    ) {
//
//        itemsRecView.adapter= ShopItemAdapter(requireContext(), itemName, intentTOProductDetails)
//
//    }
//

}