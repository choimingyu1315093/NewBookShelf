package com.example.newbookshelf.presentation.view.post.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentReadingClassJoinDialogBinding

class ReadingClassJoinDialog(private val onJoinClickListener: OnJoinClickListener) : DialogFragment() {
    private lateinit var binding: FragmentReadingClassJoinDialogBinding

    interface OnJoinClickListener {
        fun join(b: Boolean)
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
        return inflater.inflate(R.layout.fragment_reading_class_join_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReadingClassJoinDialogBinding.bind(view)

        bindViews()
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener {
            dismiss()
        }

        btnOk.setOnClickListener {
            onJoinClickListener.join(true)
            dismiss()
        }
    }
}