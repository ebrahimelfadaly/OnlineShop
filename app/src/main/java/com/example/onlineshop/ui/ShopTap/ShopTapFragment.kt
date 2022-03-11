package com.example.onlineshop.ui.ShopTap

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.entity.smart_collection.SmartCollection
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService

import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.repository.RepositoryImpl

import com.facebook.shimmer.ShimmerFrameLayout

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cart_toolbar_view.view.*
import kotlinx.android.synthetic.main.fragment_men_product.*

import kotlinx.android.synthetic.main.fragment_shop_tap.*
import kotlinx.android.synthetic.main.fragment_shop_tap.codeTextView
import kotlinx.android.synthetic.main.fragment_shop_tap.itemsRecView
import kotlinx.android.synthetic.main.fragment_shop_tap.lin
import kotlinx.android.synthetic.main.fragment_shop_tap.play
import kotlinx.android.synthetic.main.fragment_shop_tap.shimmerFrameLayout1
import kotlinx.android.synthetic.main.fragment_shop_tap.shimmerFrameLayout2
import kotlinx.android.synthetic.main.list_toolbar_view.view.*

import kotlinx.coroutines.*


class ShopTapFragment : Fragment(),ShopItemAdapter.OnclickBrand{
    private lateinit var adsAdapter: AdsAdapter
    private lateinit var  shopTabViewModel : ShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val repository = RepositoryImpl(
            RemoteDataSourceImpl(),
            RoomDataSourceImpl(RoomService.getInstance(application))
        )
        val viewModelFactory = ViewModelFactory(repository, application)
        shopTabViewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[ShopViewModel::class.java]


        return inflater.inflate(R.layout.fragment_shop_tap, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.text = "Shop"
        changeToolbar()
        val shf1 = requireActivity().findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout1)
        val shf2 = requireActivity().findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout2)

        shf1.startShimmer()
        shf2.startShimmer()

        if (NetworkChange.isOnline) {
            networkView.visibility = View.GONE
            woman_linear.visibility = View.VISIBLE

//            var list= mutableListOf<Int>()
//            list.add(R.drawable.image1)
//            list.add((R.drawable.image2))
//            list.add(R.drawable.homepage)
//            adsAdapter=AdsAdapter(requireContext())
//            adsAdapter.setAds(list)
//           viewPagerads.adapter=adsAdapter


            shopTabViewModel.fetchAllBrands().observe(viewLifecycleOwner, {
                if (it != null) {
                    shimmerFrameLayout1.stopShimmer()
                    shimmerFrameLayout2.stopShimmer()
                    shimmerFrameLayout1.visibility = View.GONE
                    shimmerFrameLayout2.visibility = View.GONE
                    itemsRecView.visibility = View.VISIBLE

                    binHomeProductRecyclerView(
                        it.smart_collections,shopTabViewModel.intentTOProductDetails

                    )
                }
            })



            shopTabViewModel.fetchallDiscountCodeList().observe(viewLifecycleOwner, {
                val allCodes = it
                if (allCodes != null) {
                    play.setOnClickListener {
                        play.visibility = View.GONE

                        GlobalScope.launch(Dispatchers.Main) {
                            delay(1500)
                            lin.visibility = View.VISIBLE
                            codeTextView.text = allCodes.discountCodes[0].code
                        }
                    }

                }
            })
        }else{
            networkView.visibility = View.VISIBLE
            woman_linear.visibility = View.GONE
        }

        codeTextView.setOnClickListener {
            val clipboard = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
            clipboard?.setPrimaryClip(ClipData.newPlainText("",codeTextView.text))
            Toast.makeText(requireContext(),"Copied", Toast.LENGTH_SHORT).show()

        }


    }

    private fun changeToolbar() {
        requireActivity().findViewById<SearchView>(R.id.mainSearchView).visibility=View.GONE
        requireActivity().findViewById<View>(R.id.nav_view).visibility = View.VISIBLE
        requireActivity().toolbar.visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.favourite).favouriteButton.setColorFilter(getResources().getColor(R.color.black))
        requireActivity().findViewById<View>(R.id.cartView).cartButton.setColorFilter(getResources().getColor(R.color.black))
        requireActivity().settingIcon.setColorFilter(getResources().getColor(R.color.black))
        requireActivity().findViewById<View>(R.id.searchIcon).visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.settingIcon).visibility = View.INVISIBLE
        requireActivity().findViewById<View>(R.id.favourite).visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.cartView).visibility = View.VISIBLE
        requireActivity().toolbar_title.setTextColor(Color.BLACK)

        requireActivity().toolbar.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        requireActivity().toolbar.setNavigationIcon(null)
    }
    private fun binHomeProductRecyclerView(
        itemName: List<SmartCollection>,
        intentTOProductDetails : MutableLiveData<SmartCollection>

    ) {

        itemsRecView.adapter = ShopItemAdapter(requireContext(), itemName,intentTOProductDetails,this)

    }

    override fun getItemProduct(smartCollection: SmartCollection, position: Int) {
        shopTabViewModel.getProductBrand(smartCollection.title)
        //  findNavController().navigate(R.id.products)


    }



}