package com.example.newbookshelf.presentation.view.chat.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentChatroomDeleteDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.chat.ChatViewModel

class ChatroomDeleteDialog(private val chatroomIdx: Int, private val onChatroomOutListener: OnChatroomOutListener) : DialogFragment() {
    private lateinit var binding: FragmentChatroomDeleteDialogBinding
    private lateinit var chatViewModel: ChatViewModel

    interface OnChatroomOutListener {
        fun outChatroom(b: Boolean)
    }

    companion object {
        const val TAG ="ChatroomDeleteDialog"
    }

    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chatroom_delete_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatroomDeleteDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        chatViewModel = (activity as HomeActivity).chatViewMode
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener {
            chatViewModel.deleteChatroom(accessToken, chatroomIdx)
        }
    }

    private fun observeViewModel() = with(binding){
        chatViewModel.deleteChatroomResult.observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "채팅을 종료하였습니다.", Toast.LENGTH_SHORT).show()
                    dismiss()
                    onChatroomOutListener.outChatroom(true)
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                else -> Unit
            }
        }
    }
}