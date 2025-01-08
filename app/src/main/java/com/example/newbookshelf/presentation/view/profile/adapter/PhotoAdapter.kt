package com.example.newbookshelf.presentation.view.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.ItemBestsellerBinding
import com.example.newbookshelf.databinding.ItemTopBookBinding

class PhotoAdapter(
    private val photoList: ArrayList<String>,
    private val context: Context,
    private val onClickItem: OnClickItem,
    private val type: Int
): RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    interface OnClickItem{
        fun selectBook(isPhoto: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTopBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(type == 1){
            holder.bind(photoList[position])
        }else {
            if(position == 0){
                holder.secondBind()
            }else {
                holder.bind(photoList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    inner class ViewHolder(private val binding: ItemTopBookBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(photo: String) = with(binding){
            Glide
                .with(ivBook)
                .load(photo)
                .into(ivBook)
        }

        fun secondBind() = with(binding){
            ivBook.setOnClickListener {
                onClickItem.selectBook(true)
            }
            Glide.with(ivBook).load(ContextCompat.getDrawable(context, R.drawable.icon_add_book)).into(ivBook)
        }
    }
}