package com.example.onlineshop.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.data.itemPojo.ProductCartModule
import com.example.onlineshop.databinding.ItemOrderConfirmBinding

class OrderItemsAdapter (
    var orderList: ArrayList<ProductCartModule>, var orderViewModel: OrderViewModel
) : RecyclerView.Adapter<OrderItemsAdapter.VH>() {

    fun updateOrder(newOrder: ProductCartModule?) {
        newOrder?.let {
            orderList.add(it)
            notifyDataSetChanged()  }

    }
    fun addNewList(orderNewList: List<ProductCartModule>) {
        orderList.clear()
        orderList.addAll(orderNewList)
        notifyDataSetChanged()

    }
    fun delItem(pos:Int) {
        orderList.removeAt(pos);
        notifyItemRemoved(pos);

    }
    class VH(var myView: ItemOrderConfirmBinding) : RecyclerView.ViewHolder(myView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewBinding =
            ItemOrderConfirmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(viewBinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.myView.itemCartQuntity.text = "x"+orderList[position].variants?.get(0)?.inventory_quantity.toString()

        Glide.with(holder.myView.itemCartImage)
            .load(orderList[position].image.src)
            .fitCenter()
            .placeholder(R.drawable.ic_loading)
            .into(holder.myView.itemCartImage)
    }

    override fun getItemCount() = orderList.size

}