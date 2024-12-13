package com.example.newbookshelf.presentation.view.chat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.chat.ChatMessageModelData
import com.example.newbookshelf.databinding.ItemChatMessageBinding

class ChatMessageAdapter: RecyclerView.Adapter<ChatMessageAdapter.ViewHolder>() {
    private val userIdx = BookShelfApp.prefs.getUserIdx("userIdx", 0)
    private var textTime = ""
    var image = ""

    private val callback = object : DiffUtil.ItemCallback<ChatMessageModelData>(){
        override fun areItemsTheSame(oldItem: ChatMessageModelData, newItem: ChatMessageModelData): Boolean {
            return oldItem.chat_message_idx == newItem.chat_message_idx
        }

        override fun areContentsTheSame(oldItem: ChatMessageModelData, newItem: ChatMessageModelData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ItemChatMessageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chatMessage: ChatMessageModelData) = with(binding){
            val parts = chatMessage.create_date.split("T", "Z")
            var date = parts[0]
            var time = parts[1].substringBefore(".")
            var preDate = ""
            var preTime = ""
            var timeWithoutSecond = time.split(":")[0] + time.split(":")[1]
            var preTimeWithoutSecond = ""
            var differentDay = false
            var isDifferentTime = false
            var isDifferentUser = false

            if (position != 0) {
                preDate = chatMessage.create_date.split("T", "Z")[0]
                if (preDate.split("-")[2] != date.split("-")[2]) {
                    differentDay = true
                }
                preTime = chatMessage.create_date.split("T", "Z")[1].substringBefore(".")
                preTimeWithoutSecond = preTime.split(":")[0] + preTime.split(":")[1]
                if (preTimeWithoutSecond != timeWithoutSecond) {
                    isDifferentTime = true
                }
            }

            if (position == 0 || differentDay) {
                tvDate.visibility = View.VISIBLE
                tvDate.text =
                    date.split("-")[0] + "년 " + date.split("-")[1] + "월 " + date.split("-")[2] + "일"

                var set = ConstraintSet()
                set.clone(cl)
                set.connect(
                    tvSender.id,
                    ConstraintSet.TOP,
                    tvDate.id,
                    ConstraintSet.BOTTOM
                )
                set.connect(
                    tvReceiver.id,
                    ConstraintSet.TOP,
                    tvDate.id,
                    ConstraintSet.BOTTOM
                )

                set.applyTo(cl)
            } else {
                tvDate.visibility = View.GONE
            }

            if (chatMessage.message_content != "") {
                var firstItemPosition = 0
                var lastItemPosition = 0

                if(position != 0){
                    for (i in position downTo 0) {
                        if (chatMessage.users.user_idx != userIdx) {
                            firstItemPosition = i + 1
                            break
                        }
                    }

                    for (i in position downTo 0) {
                        var itemTime = chatMessage.create_date.split("T", "Z")[1].substringBefore(".")
                        var itemTimeWOSecond = itemTime.split(":")[0] + itemTime.split(":")[1]
                        if (itemTimeWOSecond != timeWithoutSecond) {
                            lastItemPosition = i + 1
                            break
                        }
                    }
                }

                var hour = time.split(":")[0]
                var min = time.split(":")[1]

                if(position == 0 || differentDay){
                    val parts = chatMessage.create_date.split("T", "Z")
                    var date = parts[0]
//                    textTime = "$date $hour:$min"
                    textTime = "$hour:$min"
                }else {
                    textTime = "$hour:$min"
                }

                val message = chatMessage.message_content.replace("\\", "")

                if (userIdx == chatMessage.users.user_idx) {
                    tvSender.visibility = View.VISIBLE
                    tvSender.text = message
                    tvSendTime.visibility = View.VISIBLE
                    tvSendTime.text = textTime
                    ivPhoto.visibility = View.GONE
                } else {
                    ivPhoto.clipToOutline = true
                    ivPhoto.setOnClickListener {
//                        onClickChatListener.onFreeInfoClicked(chatMessage.users.user_idx, true)
                    }

                    if (firstItemPosition == position || differentDay) {
                        ivPhoto.visibility = View.GONE
                        if(image != ""){
                            Glide.with(ivPhoto).load(image).into(ivPhoto)
                        }else {
                            Glide.with(ivPhoto).load(R.drawable.icon_no_photo).into(ivPhoto)
                        }
                    } else {
                        ivPhoto.layoutParams.height = 1
                    }

                    tvReceiver.visibility = View.VISIBLE
                    tvReceiver.text = message
                    tvReceiveTime.visibility = View.VISIBLE
                    tvReceiveTime.text = textTime
                }
            }
        }
    }
}