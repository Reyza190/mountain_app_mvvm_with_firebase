package com.example.mountain.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mountain.R
import com.example.mountain.databinding.ListMountainBinding
import com.example.mountain.model.Mountain
import com.example.mountain.ui.MountainDetailActivity

class MountainAdapter: RecyclerView.Adapter<MountainAdapter.MountainViewHolder>() {
    private val mountain = ArrayList<Mountain>()
    private val fullList = ArrayList<Mountain>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<Mountain>){
        mountain.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun filterList(search: String){

        mountain.clear()

        for (item in mountain){
            if (item?.mountainName?.lowercase()?.contains(search.lowercase()) == true){
                mountain.add(item)
            }
        }

         notifyDataSetChanged()
    }


    class MountainViewHolder(val view: ListMountainBinding):RecyclerView.ViewHolder(view.root) {
        fun bind(mountain: Mountain){
            view.tvMountainName.text = mountain.mountainName
            Glide.with(itemView.context).load(mountain.mountainImage).into(view.ivMountain)
            view.tvLocation.text = mountain.location
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,MountainDetailActivity::class.java)
                intent.putExtra("MOUNTAIN", mountain)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainViewHolder {
        val view = ListMountainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MountainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MountainViewHolder, position: Int) {
        holder.bind(mountain[position])
    }

    override fun getItemCount(): Int {
        return mountain.size
    }
}