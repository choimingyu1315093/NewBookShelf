package com.example.newbookshelf.presentation.view.setting.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.setting.TicketLogModelData
import com.example.newbookshelf.databinding.ItemChargeLogBinding
import com.gun0912.tedpermission.provider.TedPermissionProvider.context

class ChargeLogAdapter: RecyclerView.Adapter<ChargeLogAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<TicketLogModelData>() {
        override fun areItemsTheSame(oldItem: TicketLogModelData, newItem: TicketLogModelData): Boolean {
            return oldItem.ticket_log_count == newItem.ticket_log_idx
        }

        override fun areContentsTheSame(oldItem: TicketLogModelData, newItem: TicketLogModelData): Boolean {
            return oldItem.ticket_log_idx == newItem.ticket_log_idx
        }
    }
    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChargeLogBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemChargeLogBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceType")
        fun bind(chargeLog: TicketLogModelData) = with(binding){
            tvPoint.setTextColor(Color.BLACK)

            if (chargeLog.ticket_log_count.toString().startsWith("-")) {
                tvPoint.text = "${chargeLog.ticket_log_count} 권"
            } else {
                tvPoint.setTextColor(context.resources.getColor(R.color.md_theme_primary)) // 조건에 맞는 색상
                tvPoint.text = "${chargeLog.ticket_log_count} 권"
            }

            tvDescription.text = chargeLog.ticket_log_description

            val dateTimeParts = chargeLog.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = datePart
        }
    }
}