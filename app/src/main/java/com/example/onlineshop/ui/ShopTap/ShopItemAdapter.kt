package com.example.onlineshop.ui.ShopTap

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.NavGraphDirections
import com.example.onlineshop.R
import com.example.onlineshop.data.entity.customProduct.Product
import com.example.onlineshop.data.entity.smart_collection.Brands
import com.example.onlineshop.data.entity.smart_collection.SmartCollection

class ShopItemAdapter(private val context: Context,private val itemName:List<SmartCollection>,var intentTOProductDetails : MutableLiveData<SmartCollection>,var onClickbrand:  OnclickBrand)
    :RecyclerView.Adapter<ShopItemAdapter.ViewHolder>() {
    lateinit var nav:NavController
//
//init {
//    val item=ArrayList<SmartCollection>()
//}
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val itemName: TextView = itemView.findViewById(R.id.itemTitle)
        val itemIcon : ImageView = itemView.findViewById(R.id.itemIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.shop_item,parent,false)
nav=Navigation.findNavController(parent)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bundle=Bundle().apply {
            putString("Brand",itemName[position].title)
        }

       holder.itemName.text=itemName.get(position).title

        Glide.with(context)
            .load( itemName[position].image!!.src )
            .into(holder.itemIcon)
        holder.itemView.setOnClickListener {
          nav.navigate(R.id.action_shopTabFragment2_to_products,bundle)
            Log.i("eeeeeeeeee", "onBindViewHolder:${itemName.get(position).title} ")
            intentTOProductDetails.value = itemName[position]
          onClickbrand.getItemProduct(itemName[position],position)

        }
    }

    override fun getItemCount(): Int {
       return itemName.size
    }
    interface OnclickBrand{
        fun getItemProduct(smartCollection: SmartCollection,position: Int)
    }
}