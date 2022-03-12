package com.example.onlineshop.ui.product

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlineshop.NavGraphDirections
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.entity.allproducts.allProduct
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.repository.RepositoryImpl
import com.example.onlineshop.ui.ShopTap.ShopViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_products.*


class Products : Fragment() ,ProductItemAdapter.OnclickBrand{
    private lateinit var  shopTabViewModel : ShopViewModel
    var id:Long = 0
     var args:String?=" "
    lateinit var productAdapter: ProductItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        requireActivity().toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24))
        requireActivity().toolbar.setNavigationOnClickListener {
            view?.findNavController()?.popBackStack()
        }

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
        // Inflate the layout for this fragment
        changeToolbar()
        productAdapter= ProductItemAdapter(requireContext(),shopTabViewModel.intentTOProductBrand,this)
        return inflater.inflate(R.layout.fragment_products, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

         args= arguments?.getString("Brand")
        Log.i("ebtahimmmmm", "onActivityCreated: ${args}")
        shopTabViewModel.fetchallProductsList().observe(viewLifecycleOwner,{
            var productList=it.products
            var list:MutableList<allProduct> =
                mutableListOf()
            for (i in productList)
            {
                if (i.vendor.equals(args)){
                    list.add(i)
                }
            }
            productAdapter.addList(list)
            val layoutManager=GridLayoutManager(requireContext(),2)

            itemsRecViewproduct.adapter=productAdapter
            itemsRecViewproduct.layoutManager=layoutManager
        })
        shopTabViewModel.intentTOProductBrand.observe(requireActivity(), {
            if (NetworkChange.isOnline){
                shopTabViewModel.intentTOProductDetails = MutableLiveData()
                val action = NavGraphDirections.actionGlobalProductDetailsFragment(it.id.toLong())
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireContext(),requireContext().resources.getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT).show()
            }
        })



    }
    private fun changeToolbar() {
        requireActivity().findViewById<SearchView>(R.id.mainSearchView).visibility = View.GONE

        requireActivity().findViewById<View>(R.id.nav_view).visibility = View.GONE
        requireActivity().toolbar.visibility = View.VISIBLE
        if (requireActivity().findViewById<View>(R.id.searchIcon) != null) {
            requireActivity().findViewById<View>(R.id.searchIcon).visibility = View.INVISIBLE
            requireActivity().findViewById<View>(R.id.settingIcon).visibility = View.INVISIBLE

            requireActivity().findViewById<View>(R.id.favourite).visibility = View.INVISIBLE
            requireActivity().findViewById<View>(R.id.cartView).visibility = View.VISIBLE

            requireActivity().toolbar_title.setTextColor(Color.BLACK)
            requireActivity().toolbar_title.text = "Product Brand"
        }


        requireActivity().toolbar.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        requireActivity().toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.black_arrow))
        requireActivity().toolbar.setNavigationOnClickListener {
            view?.findNavController()?.popBackStack()
        }

    }

    override fun getItemProduct(
        smartCollection: com.example.onlineshop.data.entity.customProduct.Product,
        position: Int
    ) {
        TODO("Not yet implemented")
    }


}