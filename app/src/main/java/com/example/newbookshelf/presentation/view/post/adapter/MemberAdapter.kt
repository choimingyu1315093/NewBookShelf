package com.example.newbookshelf.presentation.view.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassMembersModelData
import com.example.newbookshelf.data.model.post.readingclass.UsersX
import com.example.newbookshelf.databinding.ItemMemberBinding

class MemberAdapter: RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<ReadingClassMembersModelData>() {
        override fun areItemsTheSame(oldItem: ReadingClassMembersModelData, newItem: ReadingClassMembersModelData): Boolean {
            return oldItem.users.user_name == newItem.users.user_name
        }

        override fun areContentsTheSame(oldItem: ReadingClassMembersModelData, newItem: ReadingClassMembersModelData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemMemberBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(readingClassMember: ReadingClassMembersModelData) = with(binding){
            tvName.text = readingClassMember.users.user_name
        }
    }
}