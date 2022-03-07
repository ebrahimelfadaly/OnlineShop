package com.example.onlineshop.ui.category

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.databinding.SubcategoryTitleBinding

class SubCategoryAdapter (var subCategories:Array<String>,var subCategoryRecyclerClick: SubRecyclerClick): RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    var oldPos:Int?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoryAdapter.SubCategoryViewHolder {
        return SubCategoryViewHolder(SubcategoryTitleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun onBindViewHolder(
        holder: SubCategoryAdapter.SubCategoryViewHolder,
        position: Int
    ) {
        holder.binding.subCategoryTitle.text=subCategories.get(position)
        if (holder.adapterPosition==oldPos?:false){
            //black
            holder.binding.underLine.background= ColorDrawable(Color.parseColor("#000000"))
        }else{
            //white
            holder.binding.underLine.background= ColorDrawable(Color.parseColor("#ffffff"))
        }
        holder.itemView.setOnClickListener {
            oldPos=position
            if (holder.adapterPosition==oldPos){
                //black
                holder.binding.underLine.background= ColorDrawable(Color.parseColor("#000000"))
            }
            subCategoryRecyclerClick.onSubClick(position)

        }
    }

    override fun getItemCount(): Int {
        return subCategories.size
    }
    class SubCategoryViewHolder(val binding: SubcategoryTitleBinding): RecyclerView.ViewHolder(binding.root)
}