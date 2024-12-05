package com.example.newbookshelf.presentation.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.bestseller.Item

class AttentionBestsellerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
    var isDetail = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when(viewType){
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_bestseller, parent, false)
                NoDetailViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_book, parent, false)
                DetailViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(isDetail){
            val book = differ.currentList[position]
            if(book.cover == ""){
                Glide.with((holder as DetailViewHolder).ivBook).load(R.drawable.no_image).into((holder as DetailViewHolder).ivBook)
            }else {
                Glide.with((holder as DetailViewHolder).ivBook).load(book.cover).into((holder as DetailViewHolder).ivBook)
            }

            (holder as DetailViewHolder).tvTitle.text = book.title
            (holder as DetailViewHolder).tvWriter.text = "${book.author} ⎪ ${book.publisher}"
            (holder as DetailViewHolder).rb.visibility = View.GONE
            (holder as DetailViewHolder).tvAverage.visibility = View.GONE
        }else {
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with((holder as NoDetailViewHolder).ivBook).load(differ.currentList[position].cover).apply(options).into((holder as NoDetailViewHolder).ivBook)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        if(isDetail){
            return 2
        }else{
            return 1
        }
    }

    inner class NoDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivBook: ImageView = itemView.findViewById(R.id.ivBook)
    }

    inner class DetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivBook: ImageView = itemView.findViewById(R.id.ivBook)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvWriter: TextView = itemView.findViewById(R.id.tvWriter)
        val rb: RatingBar = itemView.findViewById(R.id.rb)
        val tvAverage: TextView = itemView.findViewById(R.id.tvAverage)
        val btnHaveBook: AppCompatButton = itemView.findViewById(R.id.btnHaveBook)
        val btnWishBook: AppCompatButton = itemView.findViewById(R.id.btnWishBook)
        val btnReadingBook: AppCompatButton = itemView.findViewById(R.id.btnReadingBook)
        val btnReadBook: AppCompatButton = itemView.findViewById(R.id.btnReadBook)
    }
}