package com.example.mountain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mountain.model.Category
import com.example.mountain.databinding.ListCategoryBinding
import kotlin.collections.ArrayList

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val category = ArrayList<Category>()
    fun submitList(data: List<Category>) {
        category.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val view: ListCategoryBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(category : Category){
            view.tvTitleCategory.text = category.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = ListCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(category[position])
    }

    override fun getItemCount(): Int {
        return category.size
    }
}