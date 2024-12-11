package com.example.newbookshelf.presentation.view.detail.adapter

import android.content.Context
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.detail.detail.DetailBookComment
import com.example.newbookshelf.databinding.ItemReviewBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    private val userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)

    private val callback = object : DiffUtil.ItemCallback<DetailBookComment>() {
        override fun areItemsTheSame(oldItem: DetailBookComment, newItem: DetailBookComment): Boolean {
            return oldItem.comment_rate == newItem.comment_rate
        }

        override fun areContentsTheSame(oldItem: DetailBookComment, newItem: DetailBookComment): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onDeleteListener: ((DetailBookComment) -> Unit)? = null
    fun setOnDeleteListener(listener: (DetailBookComment) -> Unit){
         onDeleteListener = listener
    }

    private var onUpdateListener: ((DetailBookComment) -> Unit)? = null
    fun setOnUpdateListener(listener: (DetailBookComment) -> Unit){
        onUpdateListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(review: DetailBookComment) = with(binding){
            rb.rating = review.comment_rate.toFloat()
            rb.setIsIndicator(true)
            tvAverage.text = review.comment_rate.toString()
            tvDescription.text = review.comment_content
            tvWriter.text = review.users.user_name

            if(userIdx == review.users.user_idx){
                ivMore.visibility = View.VISIBLE
                ivMore.setOnClickListener {
                    showPopupMenu(it)
                }
            }else {
                ivMore.visibility = View.GONE
            }

            val parts = review.update_date.split("T", "Z")
            val date2 = parts[0]
            val time2 = parts[1].substringBefore(".")
            val formattedTime = "$date2 $time2"

            var dt = Date()
            var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            var now = sdf.format(dt).toString()
            var date = now.split(" ")[0]
            var time = now.split(" ")[1]

            var year = date.split("-")[0].toInt()
            var month = date.split("-")[1].toInt()
            var day = date.split("-")[2].toInt()

            var hour = time.split(":")[0].toInt()
            var minute = time.split(":")[1].toInt()
            var second = time.split(":")[2].toInt()

            var lastTime = formattedTime
            var rcvdDate = lastTime.split(" ")[0]
            var rcvdTime = lastTime.split(" ")[1]

            var rcvdYear = rcvdDate.split("-")[0].toInt()
            var rcvdMonth = rcvdDate.split("-")[1].toInt()
            var rcvdDay = rcvdDate.split("-")[2].toInt()

            var rcvdHour = rcvdTime.split(":")[0].toInt()
            var rcvdMinute = rcvdTime.split(":")[1].toInt()
            var rcvdSecond = rcvdTime.split(":")[2].toInt()

            tvDate.text =
                if (rcvdYear == year) {
                    if (rcvdMonth == month) {
                        if (day == rcvdDay) {
                            if (hour == rcvdHour) {
                                if (minute == rcvdMinute) {
                                    if (second == rcvdSecond) {
                                        "방금"
                                    } else {
                                        "${rcvdSecond - second}초 전"
                                    }
                                } else {
                                    "${minute - rcvdMinute}분 전"
                                }
                            } else {
                                "${hour - rcvdHour}시간 전"
                            }
                        } else if (day - rcvdDay == 1) {
                            "어제"
                        } else {
                            rcvdDate
                        }
                    } else {
                        rcvdDate
                    }
                } else {
                    rcvdDate
                }
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