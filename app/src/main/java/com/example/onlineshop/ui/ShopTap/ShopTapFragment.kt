package com.example.onlineshop.ui.ShopTap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView;
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentShopTapBinding
import com.example.onlineshop.ui.ShopTap.shopTapcategory.KidsProductFragment
import com.example.onlineshop.ui.ShopTap.shopTapcategory.MenProductFragment
import com.example.onlineshop.ui.ShopTap.shopTapcategory.OneSaleProductFragment
import com.example.onlineshop.ui.ShopTap.shopTapcategory.WomanProductFragment
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cart_toolbar_view.view.*
import kotlinx.android.synthetic.main.fragment_shop_tap.*
import kotlinx.android.synthetic.main.list_toolbar_view.view.*


class ShopTapFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_shop_tap, container, false)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().toolbar_title.text = "Shop"
        setUpTabBar()
        changeToolbar()

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
    private fun setUpTabBar() {
        val adapter = FragmentPagerItemAdapter(
            childFragmentManager,
            FragmentPagerItems.with(activity)
                .add("Shop", WomanProductFragment::class.java)
//                .add("Kids", KidsProductFragment::class.java)
//                .add("Men", MenProductFragment::class.java)
//                .add("Sale", OneSaleProductFragment::class.java)

                .create()
        )

        viewpager.adapter = adapter
        viewpagertab.setViewPager(viewpager)


    }
}