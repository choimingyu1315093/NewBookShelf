package com.example.newbookshelf.presentation.view.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbookshelf.data.model.chat.ChatList
import com.example.newbookshelf.databinding.ItemChatListBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatListAdapter: RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<ChatList>() {
        override fun areItemsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
            return oldItem.books == newItem.books
        }

        override fun areContentsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    private var onClickListener: ((ChatList) -> Unit)? = null
    fun setOnClickListener(listener: (ChatList) -> Unit){
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemChatListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chatList: ChatList) = with(binding){
            Glide.with(ivBook).load(chatList.books.book_image).into(ivBook)
            tvName.text = chatList.opponent_user[0].users.user_name
            tvMessage.text = chatList.recent_message
            if(chatList.recent_message_date != null){
                val parts = chatList.recent_message_date.split("T", "Z")
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

                tvTime.text =
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

            if(chatList.me_user.unread_count != 0){
                tvMsgCount.visibility = View.VISIBLE
                tvMsgCount.text = chatList.me_user.unread_count.toString()
            }else {
                tvMsgCount.visibility = View.GONE
            }

            cl.setOnClickListener {
                onClickListener?.let {
                    it(chatList)
                }
            }
        }
    }
}