package com.example.newbookshelf.presentation.view.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.data.model.profile.ActivityModelData
import com.example.newbookshelf.databinding.ItemActiveBinding

class ProfileActiveAdapter: RecyclerView.Adapter<ProfileActiveAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<ActivityModelData>() {
        override fun areItemsTheSame(oldItem: ActivityModelData, newItem: ActivityModelData): Boolean {
            return oldItem.activity_point == newItem.activity_point
        }

        override fun areContentsTheSame(oldItem: ActivityModelData, newItem: ActivityModelData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemActiveBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemActiveBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(active: ActivityModelData) = with(binding){
            tvTitle.text = active.activity_title

            val dateTimeParts = active.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = "$datePart"

            tvContent.text = active.activity_content
        }
    }
}