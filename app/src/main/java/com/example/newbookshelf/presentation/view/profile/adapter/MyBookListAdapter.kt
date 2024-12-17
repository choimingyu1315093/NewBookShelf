package com.example.newbookshelf.presentation.view.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.profile.MyBookModelBook
import com.example.newbookshelf.data.model.profile.MyBookModelData
import com.example.newbookshelf.databinding.ItemSearchBookBinding
import com.example.newbookshelf.databinding.ItemSearchBookTitleBinding

class MyBookListAdapter: RecyclerView.Adapter<MyBookListAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<MyBookModelData>() {
        override fun areItemsTheSame(oldItem: MyBookModelData, newItem: MyBookModelData): Boolean {
            return oldItem.books == newItem.books
        }

        override fun areContentsTheSame(oldItem: MyBookModelData, newItem: MyBookModelData): Boolean {
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
        fun bind(myBook: MyBookModelData) = with(binding){
            if(myBook.books.book_image == ""){
                Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
            }else {
                Glide.with(ivBook).load(myBook.books.book_image).into(ivBook)
            }

            tvTitle.text = myBook.books.book_name
            tvWriter.text = "${myBook.books.book_author} ⎪ ${myBook.books.book_publisher}"
            rb.visibility = View.GONE
            tvAverage.visibility = View.GONE
        }
    }
}