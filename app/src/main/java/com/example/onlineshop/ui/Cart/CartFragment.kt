package com.example.onlineshop.ui.cart

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.NavGraphDirections
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.itemPojo.Product
import com.example.onlineshop.data.itemPojo.ProductCartModule
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.data.sharedprefrences.MeDataSharedPrefrenceReposatory
import com.example.onlineshop.databinding.FragmentCartBinding
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.repository.RepositoryImpl
import timber.log.Timber
import androidx.navigation.findNavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import androidx.navigation.NavOptions
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

class CartFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var meDataSourceReo: MeDataSharedPrefrenceReposatory

    private var customerID = ""
    private var totalPrice = 0.0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        changeToolbar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        meDataSourceReo = MeDataSharedPrefrenceReposatory(requireActivity())
        binding = FragmentCartBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application
        val repository = RepositoryImpl(
            RemoteDataSourceImpl(),
            RoomDataSourceImpl(RoomService.getInstance(application))
        )
        val viewModelFactory = ViewModelFactory(repository, application)
        orderViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(OrderViewModel::class.java)

        if (isLoged()) {
            binding.group.visibility = View.VISIBLE
            binding.emptyStateGroup.visibility = View.GONE
        } else {
            binding.group.visibility = View.GONE
            binding.emptyStateGroup.visibility = View.VISIBLE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        cartAdapter = CartAdapter(arrayListOf(), orderViewModel)
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }



        customerID = meDataSourceReo.loadUsertId()

        binding.loginBtn.setOnClickListener {
            val action = NavGraphDirections.actionGlobalSignInFragment()
            findNavController().navigate(action)
        }
        orderViewModel.getdelOrderID().observe(viewLifecycleOwner, Observer<Long> {
            deleteAlert(it)

        })
        orderViewModel.getOrderQuntity().observe(viewLifecycleOwner, Observer<Boolean> {
            calcTotal(cartAdapter.orderList)

        })
        binding.checkoutButton.setOnClickListener {
//            val action = NavGraphDirections.actionGlobalOrderConfirmationFragment(
//                totalPrice.toFloat()
//            )
//            findNavController().navigate(action)
            view.findNavController()
                .navigate(
                    CartFragmentDirections.actionCartFragment2ToOrderConfirmationFragment(
                        totalPrice.toFloat()
                    )
                )
        }

        orderViewModel.getAllCartList().observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                binding.group.visibility = View.GONE
                binding.emptyCartGroup.visibility = View.VISIBLE
            } else {
                binding.group.visibility = View.VISIBLE
                binding.emptyCartGroup.visibility = View.GONE
                var products = it.toMutableList()
                cartAdapter.addNewList(products)
                calcTotal(products)
            }

        })
        orderViewModel.getFavOrder().observe(viewLifecycleOwner, {
            favAlert(it)

        })
        orderViewModel.getDetalisOrderID().observe(viewLifecycleOwner, {
            val action = NavGraphDirections.actionGlobalProuductDetailsFragment(it.toLong())
            findNavController().navigate(action)

        })

    }

    private fun favAlert(item: ProductCartModule?) {
        val builder = AlertDialog.Builder(requireContext())
        //  builder.setTitle(De)
        builder.setMessage("Are you sure moving the product to wishlist from shopping bag?")

        builder.setPositiveButton("YES") { dialogInterface, which ->
            //  Toast.makeText(requireContext(), "clicked yes", Toast.LENGTH_LONG).show()
            if (NetworkChange.isOnline) {
                item?.let {
                    var product = Product(
                        item.id,
                        item.title,
                        item.body_html,
                        item.vendor,
                        item.product_type,
                        item.created_at,
                        item.handle,
                        item.updated_at,
                        item.published_at,
                        item.template_suffix,
                        item.status,
                        item.published_scope,
                        item.tags,
                        item.admin_graphql_api_id,
                        item.variants,
                        item.options,
                        item.images,
                        item.image
                    )
                    orderViewModel.saveWishList(product)
                    orderViewModel.delOrder(item.id)                }


            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.thereIsNoNetwork),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        builder.setNegativeButton("NO") { dialogInterface, which ->
            //  Toast.makeText(requireContext(),"clicked No",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLACK)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY)
    }

    private fun calcTotal(item: List<ProductCartModule>?) {
        val price = item?.map {
            it.variants?.get(0)?.inventory_quantity?.let { it1 ->
                it.variants?.get(0)?.price?.times(
                    it1.toDouble()
                )
            }
        }
        totalPrice = (price?.sumByDouble { it ?: 0.0 }) ?: 0.0
        binding.totalPriceText.text = (price?.sumByDouble { it ?: 0.0 }).toString() + "EGP"

        Timber.i("price" + price + "lj")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.empty_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.i("olaonDestroyView" + cartAdapter.orderList)
        if (NetworkChange.isOnline) {
            orderViewModel.insertAllOrder(cartAdapter.orderList)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.thereIsNoNetwork),
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun deleteAlert(id: Long) {

        val builder = AlertDialog.Builder(requireContext())
        //  builder.setTitle(De)
        builder.setMessage(getString(R.string.alert_msg))

        builder.setPositiveButton("Delete") { dialogInterface, which ->
            //  Toast.makeText(requireContext(), "clicked yes", Toast.LENGTH_LONG).show()
            if (NetworkChange.isOnline) {
                orderViewModel.delOrder(id)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.thereIsNoNetwork),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        builder.setNegativeButton("Cancel") { dialogInterface, which ->
            //  Toast.makeText(requireContext(),"clicked No",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLACK)
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE)
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY)
    }

    private fun isLoged(): Boolean {
        return meDataSourceReo.loadUsertstate()
    }

    private fun changeToolbar() {
        requireActivity().findViewById<android.widget.SearchView>(R.id.mainSearchView).visibility=View.GONE
        requireActivity().findViewById<View>(R.id.nav_view).visibility = View.GONE
        requireActivity().toolbar.visibility = View.VISIBLE

        requireActivity().toolbar.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        requireActivity().toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24))
        requireActivity().toolbar.setNavigationOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        //requireActivity().bottom_nav.visibility = View.VISIBLE
        requireActivity().toolbar_title.text = "Cart"
        requireActivity().findViewById<View>(R.id.searchIcon).visibility = View.INVISIBLE
        requireActivity().findViewById<View>(R.id.settingIcon).visibility = View.INVISIBLE
        requireActivity().findViewById<View>(R.id.favourite).visibility = View.INVISIBLE
        requireActivity().findViewById<View>(R.id.cartView).visibility = View.INVISIBLE
        requireActivity().toolbar_title.setTextColor(Color.WHITE)
    }

}
