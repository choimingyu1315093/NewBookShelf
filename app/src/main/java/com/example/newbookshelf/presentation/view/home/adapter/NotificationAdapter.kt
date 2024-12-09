package com.example.newbookshelf.presentation.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.data.model.home.notify.AlarmListDataResult
import com.example.newbookshelf.data.model.home.notify.AlarmListModel
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.databinding.ItemNotificationBinding

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<AlarmListDataResult>() {
        override fun areItemsTheSame(oldItem: AlarmListDataResult, newItem: AlarmListDataResult): Boolean {
            return oldItem.alarm_idx == newItem.alarm_idx
        }

        override fun areContentsTheSame(oldItem: AlarmListDataResult, newItem: AlarmListDataResult): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onClickListener: ((AlarmListDataResult) -> Unit)? = null
    fun setOnClickListener(listener: (AlarmListDataResult) -> Unit){
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(alarm: AlarmListDataResult) = with(binding){
            tvTitle.text = alarm.alarm_title
            tvContent.text = alarm.alarm_content
            ivThrash.setOnClickListener {
                onClickListener?.let {
                    it(alarm)
                }
            }
        }
    }
}