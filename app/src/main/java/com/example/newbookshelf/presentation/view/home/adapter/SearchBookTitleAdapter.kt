package com.example.newbookshelf.presentation.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.databinding.ItemSearchBookTitleBinding

class SearchBookTitleAdapter: RecyclerView.Adapter<SearchBookTitleAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<SearchedBook>() {
        override fun areItemsTheSame(oldItem: SearchedBook, newItem: SearchedBook): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: SearchedBook, newItem: SearchedBook): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onClickListener: ((SearchedBook) -> Unit)? = null
    fun setOnClickListener(listener: (SearchedBook) -> Unit){
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchBookTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    
    inner class ViewHolder(private val binding: ItemSearchBookTitleBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(searchedBook: SearchedBook) = with(binding){
            tvTitle.text = searchedBook.title

            root.setOnClickListener {
                onClickListener?.let {
                    it(searchedBook)
                }
            }
        }
    }
}