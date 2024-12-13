package com.example.newbookshelf.presentation.view.map.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.chat.CreateChatroomData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentNearBookDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.map.MapViewModel

class NearBookDialog(
    private val title: String,
    private val img: String,
    private val name: String,
    private val userIdx: Int,
    private val isbn: String,
    private val onDialogClose: OnDialogClose
) : DialogFragment() {

    private lateinit var binding: FragmentNearBookDialogBinding
    private lateinit var mapViewModel: MapViewModel
    
    interface OnDialogClose{
        fun isClose(b: Boolean, chatroomIdx: Int, name: String)
    }

    companion object {
        const val TAG = "NearBookDialog"
    }

    private lateinit var accessToken: String
    private var buttonClicked = false

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
        return inflater.inflate(R.layout.fragment_near_book_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNearBookDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        mapViewModel = (activity as HomeActivity).mapViewModel
        
        txtWishBook.text = "${name}님이 읽고 싶었던"
        Glide.with(ivBook).load(img).into(ivBook)
        tvTitle.text = "<${title}>"
    }

    private fun bindViews() = with(binding){
        btnOk.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            buttonClicked = true
            val createChatroomData = CreateChatroomData(isbn, userIdx)
            mapViewModel.createChatroom(accessToken, createChatroomData)
        }
    }

    private fun observeViewModel() = with(binding){
        mapViewModel.createChatroomResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    if(buttonClicked){
                        if(response.data?.data?.description != null){
                            Toast.makeText(activity, response.data.data.description, Toast.LENGTH_SHORT).show()
                        }else {
                            onDialogClose.isClose(true, response.data?.data!!.chat_room_idx, name)
                        }
                        dismiss()
                        buttonClicked = false
                        progressBar.visibility = View.GONE
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }
}