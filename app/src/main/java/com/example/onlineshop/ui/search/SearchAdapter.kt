package com.example.onlineshop.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.data.entity.allproducts.allProduct


class SearchAdapter(private val context: Context,private val itemName:List<allProduct>) :RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var filterList : List<allProduct>

    init {
        filterList = itemName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.shop_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text=filterList.get(position).vendor
           Glide.with(context)
               .load(filterList.get(position).image.src)
               .into(holder.itemIcon)
    }

    override fun getItemCount(): Int {
      return filterList.size
    }



    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val itemName: TextView = itemView.findViewById(R.id.itemTitle)
        val itemIcon : ImageView = itemView.findViewById(R.id.itemIcon)
    }

}