package com.example.onlineshop.ui.ShopTap

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.data.entity.customProduct.Product
import com.example.onlineshop.data.entity.smart_collection.Brands
import com.example.onlineshop.data.entity.smart_collection.SmartCollection

class ShopItemAdapter(private val context: Context,private val itemName:List<SmartCollection>,var intentTOProductDetails : MutableLiveData<SmartCollection>,var onClickbrand:  OnclickBrand)
    :RecyclerView.Adapter<ShopItemAdapter.ViewHolder>() {

init {
    val item=ArrayList<SmartCollection>()
}
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val itemName: TextView = itemView.findViewById(R.id.itemTitle)
        val itemIcon : ImageView = itemView.findViewById(R.id.itemIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.shop_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.itemName.text=itemName.get(position).title

        Glide.with(context)
            .load( itemName[position].image!!.src )
            .into(holder.itemIcon)
        holder.itemView.setOnClickListener {
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