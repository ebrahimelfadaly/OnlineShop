package com.example.onlineshop.ui.ShopTap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.R
import com.makeramen.roundedimageview.RoundedImageView

class AdsAdapter(val context: Context): RecyclerView.Adapter<AdsAdapter.AdsViewHolder>() {

lateinit var list:List<Int>
   fun setAds(list: List<Int>)
   {
       this.list=list
       notifyDataSetChanged()
   }

    class AdsViewHolder(view: View):RecyclerView.ViewHolder(view){
        var image=itemView.findViewById<RoundedImageView>(R.id.list_item_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
       val view =LayoutInflater.from(parent.context).inflate(R.layout.image_slider,parent,false)
    return AdsViewHolder(view)

    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        holder.image.setImageResource(list[position])
    }

    override fun getItemCount()=list.size

}