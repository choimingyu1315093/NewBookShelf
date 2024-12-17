package com.example.newbookshelf.presentation.view.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.data.model.post.kakao.Document
import com.example.newbookshelf.databinding.ItemKakaoBinding

class KakaoAdapter: RecyclerView.Adapter<KakaoAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem.x == newItem.x
        }

        override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onItemClickListener: ((Document) -> Unit)? = null
    fun setOnClickListener(listener: (Document) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemKakaoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemKakaoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Document) = with(binding){
            tvAddress.text = place.road_address_name
            tvName.text = place.place_name
            cl.setOnClickListener {
                onItemClickListener?.let {
                    it(place)
                }
            }
        }
    }
}