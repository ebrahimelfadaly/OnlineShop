package com.example.onlineshop.ui.category

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView;
import android.widget.Toast

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.NavGraphDirections

import kotlinx.android.synthetic.main.fragment_category.shimmerFrameLayout1
import kotlinx.android.synthetic.main.fragment_category.shimmerFrameLayout2
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.entity.customProduct.Product
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.databinding.FragmentCategoryBinding
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.repository.RepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cart_toolbar_view.view.*
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.list_toolbar_view.view.*


class CategoryFragment : Fragment() ,SubRecyclerClick,MainRecyclerClick,ItemRecyclerClick{
    var mainCategoryIndex=0
    var subCategoryIndex=0
    var colID:Long=268359696582
    lateinit var catViewModel:CategoryViewModel
    lateinit var products:List<Product>
    lateinit var subList:List<Product>
    lateinit var  subcatList:Array<String>
    private var _binding: FragmentCategoryBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val remoteDataSource = RemoteDataSourceImpl()
        val repository = RepositoryImpl(
            RemoteDataSourceImpl(),
            RoomDataSourceImpl(RoomService.getInstance(application))
        )
        val viewModelFactory = ViewModelFactory(repository, application)
        catViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(CategoryViewModel::class.java)
        Log.i("output","one")
        return view
    }




    override fun onSubClick(position: Int) {
        super.onSubClick(position)
        Log.i("output","two")
        subCategoryIndex=position
        subList=getSubCategoryItems(position)
        if (subList.size!=0) {
            binding.placeHolder.visibility=View.GONE
            binding.itemsRecView.adapter = ItemCategoryAdapter(subList, requireContext(), this)
        }else{
            binding.itemsRecView.adapter = ItemCategoryAdapter(subList, requireContext(), this)
            binding.placeHolder.visibility=View.VISIBLE
        }
        binding.subcategoryRecView.adapter!!.notifyDataSetChanged()
    }
    override fun onMainClick(position: Int) {
        super.onMainClick(position)
        Log.i("output","three")

        binding.subcategoryRecView.adapter=SubCategoryAdapter(subcatList,this)
        binding.subcategoryRecView.adapter!!.notifyDataSetChanged()
        mainCategoryIndex=position
        colID=getMainCategory(position)
        if (NetworkChange.isOnline) {
            networkCatView.visibility = View.GONE
            categoryLayout.visibility = View.VISIBLE
           catViewModel.fetchCatProducts(colID).observe(requireActivity(),{
               products = it
               binding.placeHolder.visibility=View.GONE
               binding.itemsRecView.adapter = ItemCategoryAdapter(products, requireContext(), this)
               Log.d("hitler", "list size: " + it.size)
               binding.itemsRecView.adapter!!.notifyDataSetChanged()

           })

        }else{
            networkCatView.visibility = View.VISIBLE
            categoryLayout.visibility = View.GONE

        }
        binding.mainCategoryRecView.adapter!!.notifyDataSetChanged()


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("output","five")

        binding.shimmerFrameLayout1.startShimmer()
        binding.shimmerFrameLayout2.startShimmer()
        binding.shimmerFrameLayout3.startShimmer()
        binding.shimmerFrameLayout4.startShimmer()
        changeToolbar()
        subcatList= arrayOf("Shoes","Accessories","T-Shirts")
        var mainCatList= arrayOf("Home","kids","Men","Sales","Women")
        binding.subcategoryRecView.adapter= SubCategoryAdapter(subcatList,this)
        binding.mainCategoryRecView.adapter= MainCategoryAdapter(mainCatList,this)
        if (NetworkChange.isOnline) {

            networkCatView.visibility = View.GONE
            categoryLayout.visibility = View.VISIBLE
            catViewModel.fetchCatProducts(267715608774).observe(requireActivity(), {
                if(it != null){
                    shimmerFrameLayout1.stopShimmer()
                    shimmerFrameLayout2.stopShimmer()
                    shimmerFrameLayout3.stopShimmer()
                    shimmerFrameLayout4.stopShimmer()

                    shimmerFrameLayout1.visibility = View.GONE
                    shimmerFrameLayout2.visibility = View.GONE
                    shimmerFrameLayout3.visibility = View.GONE
                    shimmerFrameLayout4.visibility = View.GONE

                    binding.itemsRecView.visibility = View.VISIBLE
                    products = it
                    binding.itemsRecView.adapter = ItemCategoryAdapter(products, requireContext(), this)
                }

            })
        }else{
            networkCatView.visibility = View.VISIBLE
            categoryLayout.visibility = View.GONE
        }

    }

    fun getMainCategory(position:Int):Long{
        var main: Long
        when(position){
            0-> main=398034632935
            1-> main=398034632935//right kids
            2-> main=398034567399//right men
            3-> main=398034665703//right on sale
            4-> main=398034600167// right woman
            else-> main=0
        }
        return main
    }
    fun getSubCategoryItems(position:Int):List<Product>{
        lateinit var subCatList:List<Product>
        when(position){
            0-> subCatList=products.filter { it.productType.equals("SHOES")}
            1-> subCatList=products.filter { it.productType.equals("ACCESSORIES")}
            2-> subCatList=products.filter { it.productType.equals("T-SHIRTS")}
            else-> subCatList=products.filter { it.productType.equals("SHOES")}
        }
        return subCatList
    }

    override fun itemOnClick(itemId: Long) {
        if (NetworkChange.isOnline){
            val action = NavGraphDirections.actionGlobalProuductDetailsFragment(itemId)
            findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(),requireContext().resources.getString(R.string.no_internet_connection),
                Toast.LENGTH_SHORT).show()
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
        requireActivity().toolbar_title.text = "Category"


    }

    override fun onDetach() {
        super.onDetach()
    }
}