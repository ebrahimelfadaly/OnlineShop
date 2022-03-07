package com.example.onlineshop.ui.category

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.databinding.SubcategoryTitleBinding

class MainCategoryAdapter( var mainCategories: Array<String>,
                           var mainRecyclerClick: MainRecyclerClick): RecyclerView.Adapter<MainCategoryAdapter.MainViewHolder>() {


    var oldPostion = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MainViewHolder {
        return MainViewHolder(
            SubcategoryTitleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.subCategoryTitle.text = mainCategories.get(position)
        if (holder.adapterPosition == oldPostion) {
            holder.binding.underLine.background = ColorDrawable(Color.parseColor("#000000"))
        } else {
            holder.binding.underLine.background = ColorDrawable(Color.parseColor("#ffffff"))
        }

        holder.itemView.setOnClickListener {
            holder.binding.underLine.background = ColorDrawable(Color.parseColor("#000000"))
            mainRecyclerClick.onMainClick(position)
            oldPostion = position
        }
    }

    override fun getItemCount(): Int {
        return mainCategories.size
    }

    class MainViewHolder(val binding: SubcategoryTitleBinding) :
        RecyclerView.ViewHolder(binding.root)

}