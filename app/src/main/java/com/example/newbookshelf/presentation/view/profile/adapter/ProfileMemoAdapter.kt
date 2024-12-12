package com.example.newbookshelf.presentation.view.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.data.model.profile.MemoModelData
import com.example.newbookshelf.databinding.ItemMemoBinding

class ProfileMemoAdapter: RecyclerView.Adapter<ProfileMemoAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<MemoModelData>() {
        override fun areItemsTheSame(oldItem: MemoModelData, newItem: MemoModelData): Boolean {
            return oldItem.memo_idx == newItem.memo_idx
        }

        override fun areContentsTheSame(oldItem: MemoModelData, newItem: MemoModelData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemMemoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(memo: MemoModelData) = with(binding){
            tvContent.text = memo.memo_content

            val dateTimeParts = memo.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = "${memo.books.book_name}    $datePart"
        }
    }
}