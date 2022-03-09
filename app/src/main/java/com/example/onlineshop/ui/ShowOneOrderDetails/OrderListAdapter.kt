package com.example.onlineshop.ui.ShowOneOrderDetails

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.data.entity.orderGet.GetOrders

class OrdersListAdapter(line_items: List<GetOrders.Order.LineItem?>,images : List<String>?) : RecyclerView.Adapter<OrdersListAdapter.ViewHolder>() {
    var line_items: List<GetOrders.Order.LineItem?> = line_items
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    var images:  List<String>? = images
        set(value) {
            field=value
            notifyDataSetChanged()
        }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val  title =itemView.findViewById<TextView>(R.id.title)
        val  priceEdible =itemView.findViewById<TextView>(R.id.priceEdible)
        val  variant_Title =itemView.findViewById<TextView>(R.id.variant_Title)
        val  quantityEditable =itemView.findViewById<TextView>(R.id.quantityEditable)
        val  itemImageOrder =itemView.findViewById<ImageView>(R.id.itemImageOrder)
        val color = itemView.findViewById<TextView>(R.id.color)
        fun binding(lineItem: GetOrders.Order.LineItem?, image :String?) {
            title.text = lineItem?.title ?: "Not Known"
            priceEdible.text = lineItem?.price+"EGP"
            var size = lineItem?.variant_title ?: "Not Known"
            variant_Title.text = size.split("/").get(0)
            color.text = size.split("/").get(1)
            Log.i("output",lineItem?.variant_title ?: "Not Known")
            quantityEditable.text = lineItem?.quantity.toString()+"X"
            Glide.with(itemImageOrder)
                .load(image?: "")
                .fitCenter()
                .placeholder(R.drawable.ic_loading)
                .into(itemImageOrder)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.one_order_details, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (images!!.isEmpty()){
            holder.binding(line_items[position], "")
        }else{
            holder.binding(line_items[position], images?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return line_items.size
    }
}