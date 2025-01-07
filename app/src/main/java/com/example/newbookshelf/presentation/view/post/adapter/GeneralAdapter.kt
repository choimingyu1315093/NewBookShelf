package com.example.newbookshelf.presentation.view.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.data.model.post.general.PostModelData
import com.example.newbookshelf.data.model.post.kakao.Document
import com.example.newbookshelf.databinding.ItemGeneralBinding

class GeneralAdapter: RecyclerView.Adapter<GeneralAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<PostModelData>() {
        override fun areItemsTheSame(oldItem: PostModelData, newItem: PostModelData): Boolean {
            return oldItem.post_title == newItem.post_title
        }

        override fun areContentsTheSame(oldItem: PostModelData, newItem: PostModelData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onItemClickListener: ((PostModelData) -> Unit)? = null
    fun setOnClickListener(listener: (PostModelData) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralAdapter.ViewHolder {
        return ViewHolder(ItemGeneralBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GeneralAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemGeneralBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(post: PostModelData) = with(binding){
            tvTitle.text = post.post_title
            tvName.text = post.user_name

            if(post.post_comment_count != 0){
                tvCount.text = "[${post.post_comment_count}]"
            }else {
                tvCount.visibility = View.GONE
            }

            tvTime.text = post.create_date.split("T")[0]
            cl.setOnClickListener {
                onItemClickListener?.let {
                    it(post)
                }
            }
        }
    }
}