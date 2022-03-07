package com.example.onlineshop.ui.product

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.repository.RepositoryImpl
import com.example.onlineshop.ui.ShopTap.ShopViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_products.*


class Products : Fragment() {
    private lateinit var  shopTabViewModel : ShopViewModel
    var id:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: ProductsArgs by navArgs()
        id = args.id

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
        return inflater.inflate(R.layout.fragment_products, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        if (NetworkChange.isOnline) {

            shopTabViewModel.fetchGetProductB(id).observe(viewLifecycleOwner, {
                if (it != null) {

               bindProductRecyclerView(it,shopTabViewModel.intentTOProductBrand)

                }
            })

            //product details fragment




        }else{

        }



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



    private fun bindProductRecyclerView(
        itemName: List<com.example.onlineshop.data.entity.customProduct.Product>,
        intentTOProductDetails: MutableLiveData<com.example.onlineshop.data.entity.customProduct.Product>
    ) {

        itemsRecViewproduct.adapter = ProductItemAdapter(requireContext(), itemName,intentTOProductDetails)

    }




}