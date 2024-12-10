package com.example.newbookshelf.presentation.view.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.data.model.detail.memo.GetBookMemoModelData
import com.example.newbookshelf.databinding.ItemMemoBinding

class MemoAdapter: RecyclerView.Adapter<MemoAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<GetBookMemoModelData>() {
        override fun areItemsTheSame(oldItem: GetBookMemoModelData, newItem: GetBookMemoModelData): Boolean {
            return oldItem.memo_content == newItem.memo_content
        }

        override fun areContentsTheSame(oldItem: GetBookMemoModelData, newItem: GetBookMemoModelData): Boolean {
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
        fun bind(memo: GetBookMemoModelData) = with(binding){
            tvContent.text = memo.memo_content

            val dateTimeParts = memo.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = "${memo.users.user_name}    $datePart"
        }
    }
}