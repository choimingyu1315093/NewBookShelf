package com.example.newbookshelf.presentation.view.post.adapter

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
import com.example.newbookshelf.data.model.post.general.PostDetailComment
import com.example.newbookshelf.databinding.ItemGeneralReviewBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GeneralDetailReviewAdapter: RecyclerView.Adapter<GeneralDetailReviewAdapter.ViewHolder>() {

    private val userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)

    private val callback = object : DiffUtil.ItemCallback<PostDetailComment>() {
        override fun areItemsTheSame(oldItem: PostDetailComment, newItem: PostDetailComment): Boolean {
            return oldItem.update_date == newItem.update_date
        }

        override fun areContentsTheSame(oldItem: PostDetailComment, newItem: PostDetailComment): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onDeleteListener: ((PostDetailComment) -> Unit)? = null
    fun setOnDeleteListener(listener: (PostDetailComment) -> Unit){
        onDeleteListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemGeneralReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemGeneralReviewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(postComment: PostDetailComment) = with(binding){
            tvName.text = postComment.users.user_name
            tvContent.text = postComment.comment_content
            if(userIdx == postComment.users.user_idx){
                ivMore.visibility = View.VISIBLE
                ivMore.setOnClickListener {
                    showPopupMenu(it)
                }
            }else {
                ivMore.visibility = View.GONE
            }

            val parts = postComment.update_date.split("T", "Z")
            val date2 = parts[0]
            val time2 = parts[1].substringBefore(".")
            val formattedTime = "$date2 $time2"

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val currentTime = sdf.parse(sdf.format(Date())) ?: Date()
            val receivedTime = sdf.parse(formattedTime) ?: Date()

            val diffInMillis = currentTime.time - receivedTime.time
            val diffInSeconds = diffInMillis / 1000
            val diffInMinutes = diffInSeconds / 60
            val diffInHours = diffInMinutes / 60
            val diffInDays = diffInHours / 24

            tvTime.text = when {
                diffInSeconds < 60 -> "방금"
                diffInMinutes < 60 -> "${diffInMinutes}분 전"
                diffInHours < 24 -> "${diffInHours}시간 전"
                diffInDays == 1L -> "어제"
                diffInDays > 1L -> date2
                else -> date2
            }
        }

        private fun showPopupMenu(anchor: View) {
            val popupMenu = PopupMenu(anchor.context, anchor)
            popupMenu.menuInflater.inflate(R.menu.comment_review_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
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