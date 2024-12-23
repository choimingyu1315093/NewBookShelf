package com.example.newbookshelf.presentation.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.bestseller.Item
import com.example.newbookshelf.databinding.ItemSearchBookBinding

class DetailAttentionBestsellerAdapter: RecyclerView.Adapter<DetailAttentionBestsellerAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemSearchBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Item) = with(binding){
            if(item.cover == ""){
                Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
            }else {
                Glide.with(ivBook).load(item.cover).into(ivBook)
            }

            tvTitle.text = item.title
            tvWriter.text = "${item.author} ⎪ ${item.publisher}"
            rb.visibility = View.GONE
            tvAverage.visibility = View.GONE
        }
    }
}