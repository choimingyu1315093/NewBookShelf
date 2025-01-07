package com.example.newbookshelf.presentation.view.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.post.readingclass.ReadingClassModelData
import com.example.newbookshelf.data.util.DateFormat
import com.example.newbookshelf.databinding.ItemReadingBinding
import java.util.Date

class ReadingClassAdapter: RecyclerView.Adapter<ReadingClassAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<ReadingClassModelData>() {
        override fun areItemsTheSame(oldItem: ReadingClassModelData, newItem: ReadingClassModelData): Boolean {
            return oldItem.club_meet_date == newItem.club_meet_date
        }

        override fun areContentsTheSame(oldItem: ReadingClassModelData, newItem: ReadingClassModelData): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)

    private var onItemClickListener: ((ReadingClassModelData) -> Unit)? = null
    fun setOnItemClickListener(listener: (ReadingClassModelData) -> Unit){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemReadingBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(readingClass: ReadingClassModelData) = with(binding){
            tvTitle.text = readingClass.book_name
            tvContent.text = readingClass.post_title
            tvDate.text = DateFormat.convertToCustomFormat(readingClass.club_meet_date)

            if(DateFormat.isDatePast(readingClass.club_meet_date)){
                tvStatus.text = "종료"
                tvStatus.setBackgroundResource(R.drawable.btn_e9e9e9_no_10)
            }else {
                tvStatus.text = "예정"
                tvStatus.setBackgroundResource(R.drawable.btn_main_no_10)
            }

            if(readingClass.book_image == ""){
                Glide.with(ivBook).load(R.drawable.no_image).into(ivBook)
            }else {
                Glide.with(ivBook).load(readingClass.book_image).into(ivBook)
            }

            cl.setOnClickListener {
                onItemClickListener?.let {
                    it(readingClass)
                }
            }
        }
    }
}