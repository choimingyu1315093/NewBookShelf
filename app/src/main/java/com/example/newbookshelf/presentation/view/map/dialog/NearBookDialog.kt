package com.example.newbookshelf.presentation.view.map.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentNearBookDialogBinding

class NearBookDialog(
    private val title: String,
    private val img: String,
    private val name: String,
    private val userIdx: Int,
    private val isbn: String,
    private val onDialogClose: OnDialogClose
) : DialogFragment() {

    private lateinit var binding: FragmentNearBookDialogBinding

    interface OnDialogClose{
        fun isClose(b: Boolean, chatroomIdx: Int, name: String)
    }

    companion object {
        const val TAG = "NearBookDialog"
    }

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
        txtWishBook.text = "${name}님이 읽고 싶었던"
        Glide.with(ivBook).load(img).into(ivBook)
        tvTitle.text = "<${title}>"
    }

    private fun bindViews() = with(binding){

    }

    private fun observeViewModel() = with(binding){

    }
}