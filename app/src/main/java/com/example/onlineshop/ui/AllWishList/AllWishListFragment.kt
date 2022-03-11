package com.example.onlineshop.ui.AllWishList

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlineshop.NavGraphDirections
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.itemPojo.Product
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.databinding.FragmentAllWishListBinding
import com.example.onlineshop.repository.RepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*


class AllWishListFragment : Fragment() {
 private lateinit var bindingAllWishListFragment: FragmentAllWishListBinding
  private lateinit var wishListAdapter: WishListAdapter
  private lateinit var allWishListViewModel: AllWishListViewModel
  private lateinit var wishListData:List<Product>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
          changeToolbar()
         bindingAllWishListFragment= FragmentAllWishListBinding.inflate(inflater,container,false)

        val repository=RepositoryImpl(RemoteDataSourceImpl(), RoomDataSourceImpl(RoomService.getInstance(requireActivity().application)))
          val viewModelFactory= ViewModelFactory(repository,requireActivity().application)
       allWishListViewModel= ViewModelProvider(requireActivity(),viewModelFactory)[AllWishListViewModel::class.java]
        wishListData=ArrayList()
      wishListAdapter= WishListAdapter(wishListData,
          allWishListViewModel.intentTOProductDetails,
              allWishListViewModel.deleteItem)
          bindingAllWishListFragment.wishRecyclerView.apply {
            this.adapter=wishListAdapter
           this.layoutManager=GridLayoutManager(requireContext(),2)
          }

         allWishListViewModel.getAllWishList().observe(requireActivity(),{
             if (it.isEmpty()) {
                 bindingAllWishListFragment.emptyAnimationView2.visibility = View.VISIBLE
                 bindingAllWishListFragment.emptyText2.visibility = View.VISIBLE
             } else {
                 bindingAllWishListFragment.emptyText2.visibility = View.GONE
                 bindingAllWishListFragment.emptyAnimationView2.visibility = View.GONE
             }

             wishListData = it
             wishListAdapter.productList = wishListData
             wishListAdapter.notifyDataSetChanged()
         })


        allWishListViewModel.intentTOProductDetails.observe(requireActivity(), {

            if (it != null) {
                val action = NavGraphDirections.actionGlobalProductDetailsFragment(it.id)
                findNavController().navigate(action)

            }
        })
             allWishListViewModel.deleteItem.observe(viewLifecycleOwner,{
                 deleteAlert(it.id)
             })


        return bindingAllWishListFragment.root
    }

    private fun deleteAlert(id: Long) {
        val builder = AlertDialog.Builder(requireContext())
        //  builder.setTitle(De)
        builder.setTitle(getString(R.string.Delete_Item_From_Wish_List))
        builder.setMessage(getString(R.string.are_you_sure))

        builder.setPositiveButton("Delete") { _, _ ->
            allWishListViewModel.deleteOneItemFromWishList(id)
            allWishListViewModel.deleteItem = MutableLiveData<Product>()
        }

        builder.setNegativeButton("Cancel") { _, _ ->
            allWishListViewModel.deleteItem = MutableLiveData<Product>()
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

    private fun changeToolbar() {
        requireActivity().findViewById<SearchView>(R.id.mainSearchView).visibility=View.GONE

        requireActivity().findViewById<View>(R.id.nav_view).visibility = View.GONE
        requireActivity().toolbar.visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.searchIcon).visibility = View.INVISIBLE
        requireActivity().findViewById<View>(R.id.settingIcon).visibility = View.INVISIBLE
        requireActivity().findViewById<View>(R.id.favourite).visibility = View.GONE
        requireActivity().findViewById<View>(R.id.cartView).visibility = View.INVISIBLE
        requireActivity().toolbar_title.setTextColor(Color.WHITE)

        requireActivity().toolbar.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        requireActivity().toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24))
        requireActivity().toolbar.setNavigationOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        requireActivity().toolbar_title.text = getString(R.string.AllWishList)
    }

    override fun onStop() {
        super.onStop()
    allWishListViewModel.intentTOProductDetails=MutableLiveData<Product>()
    }


}