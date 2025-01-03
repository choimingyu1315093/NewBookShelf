package com.example.newbookshelf.presentation.view.detail.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.detail.memo.GetBookMemoModelData
import com.example.newbookshelf.databinding.ItemMemoBinding

class MemoAdapter: RecyclerView.Adapter<MemoAdapter.ViewHolder>() {
    private val userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)

    private val callback = object : DiffUtil.ItemCallback<GetBookMemoModelData>() {
        override fun areItemsTheSame(oldItem: GetBookMemoModelData, newItem: GetBookMemoModelData): Boolean {
            return oldItem.memo_content == newItem.memo_content
        }

        override fun areContentsTheSame(oldItem: GetBookMemoModelData, newItem: GetBookMemoModelData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onDeleteListener: ((GetBookMemoModelData) -> Unit)? = null
    fun setOnDeleteListener(listener: (GetBookMemoModelData) -> Unit){
       onDeleteListener = listener
    }

    private var onUpdateListener: ((GetBookMemoModelData) -> Unit)? = null
    fun setOnUpdateListener(listener: (GetBookMemoModelData) -> Unit){
        onUpdateListener = listener
    }

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

            if(userIdx == memo.users.user_idx){
                ivMore.visibility = View.VISIBLE
                ivMore.setOnClickListener {
                    showPopupMenu(it)
                }
            }else {
                ivMore.visibility = View.GONE
            }

            val dateTimeParts = memo.create_date.split("T")
            val datePart = dateTimeParts[0]
            tvDate.text = "${memo.users.user_name}    $datePart"
        }

        private fun showPopupMenu(anchor: View) {
            val popupMenu = PopupMenu(anchor.context, anchor)
            popupMenu.menuInflater.inflate(R.menu.review_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.update -> {
                        onUpdateListener?.let {
                            it(differ.currentList[adapterPosition])
                        }
                        true
                    }
                    R.id.delete -> {
                        onDeleteListener?.let {
                            it(differ.currentList[adapterPosition])
                        }
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }
    }
}