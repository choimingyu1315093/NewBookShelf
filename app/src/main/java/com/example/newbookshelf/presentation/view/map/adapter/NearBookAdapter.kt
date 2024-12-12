package com.example.newbookshelf.presentation.view.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbookshelf.data.model.map.WishBookHaveUserModel
import com.example.newbookshelf.data.model.map.WishBookHaveUserModelData
import com.example.newbookshelf.databinding.ItemNearBookBinding

class NearBookAdapter: RecyclerView.Adapter<NearBookAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<WishBookHaveUserModelData>() {
        override fun areItemsTheSame(oldItem: WishBookHaveUserModelData, newItem: WishBookHaveUserModelData): Boolean {
            return oldItem.book_isbn == newItem.book_isbn
        }

        override fun areContentsTheSame(oldItem: WishBookHaveUserModelData, newItem: WishBookHaveUserModelData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onClickListener: ((WishBookHaveUserModelData) -> Unit)? = null
    fun setOnClickListener(listener: (WishBookHaveUserModelData) -> Unit){
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNearBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemNearBookBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(nearBook: WishBookHaveUserModelData) = with(binding){
            tvTitle.text = nearBook.book_name
            if(nearBook.book_author == ""){
                tvWriter.text = "${nearBook.book_publisher}"
            }else {
                tvWriter.text = "${nearBook.book_author} ⎪ ${nearBook.book_publisher}"
            }

            tvUser.text = "${nearBook.user_name}님이 보유 중인 책이에요."
            tvDistance.text = "${metersToKilometers(nearBook.distance!!.toDouble())}km"
            Glide.with(ivBook).load(nearBook.book_image).into(ivBook)

            cl.setOnClickListener {
                onClickListener?.let {
                    it(nearBook)
                }
            }
        }

        fun metersToKilometers(meters: Double): Double{
            return meters/1000
        }
    }
}