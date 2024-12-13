package com.example.newbookshelf.presentation.view.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
import com.example.newbookshelf.databinding.ItemSearchBookBinding
import java.lang.Math.round

class SearchMoreBookAdapter: RecyclerView.Adapter<SearchMoreBookAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<SearchBookResult>() {
        override fun areItemsTheSame(oldItem: SearchBookResult, newItem: SearchBookResult): Boolean {
            return oldItem.book_name == newItem.book_name
        }

        override fun areContentsTheSame(oldItem: SearchBookResult, newItem: SearchBookResult): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onItemClickListener: ((SearchBookResult) -> Unit)? = null
    fun setOnItemClickListener(listener: (SearchBookResult) -> Unit){
        onItemClickListener = listener
    }

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
        fun bind(book: SearchBookResult) = with(binding){
            if(book.book_image == ""){
                Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
            }else {
                Glide.with(ivBook).load(book.book_image).into(ivBook)
            }

            tvTitle.text = book.book_name
            tvWriter.text = "${book.book_author} ⎪ ${book.book_publisher}"
            rb.setIsIndicator(true)
            if(book.book_average_rate != null){
                rb.rating = book.book_average_rate.toFloat()
                val percent = book.book_average_rate
                val roundedPercent = round(percent).toInt()
//            tvAverage.text = roundedPercent.toString()
                tvAverage.text = book.book_average_rate.toString()
            }

            if(book.is_have_book != null && book.is_have_book){
                btnHaveBook.visibility = View.VISIBLE
            }else {
                btnHaveBook.visibility = View.GONE
            }

            if(book.read_type != null){
                when(book.read_type){
                    "wish" -> {
                        btnWishBook.visibility = View.VISIBLE
                    }
                    "reading" -> {
                        btnReadingBook.visibility = View.VISIBLE
                    }
                    "read" -> {
                        btnReadBook.visibility = View.VISIBLE
                    }
                }
            }else {
                btnWishBook.visibility = View.GONE
                btnReadingBook.visibility = View.GONE
                btnReadBook.visibility = View.GONE
            }

            cl.setOnClickListener {
                onItemClickListener?.let {
                    it(book)
                }
            }
        }
    }
}