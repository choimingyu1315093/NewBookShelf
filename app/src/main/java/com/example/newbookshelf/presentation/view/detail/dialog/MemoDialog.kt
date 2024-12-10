package com.example.newbookshelf.presentation.view.detail.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentMemoDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel

class MemoDialog(private val bookIsbn: String, private val onDialogCloseListener: OnDialogCloseListener) : DialogFragment() {
    private lateinit var binding: FragmentMemoDialogBinding
    private lateinit var detailViewModel: DetailViewModel

    interface OnDialogCloseListener {
        fun memoReload(b: Boolean)
    }

    companion object {
        const val TAG = "MemoDialog"
    }

    private lateinit var accessToken: String
    private var memo = ""
    private var isPublic = "y"

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
        return inflater.inflate(R.layout.fragment_memo_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMemoDialogBinding.bind(view)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        detailViewModel = (activity as HomeActivity).detailViewModel
    }

    private fun bindViews() = with(binding){
        etMemo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                memo = s.toString().trim()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        rg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb1 -> {
                    isPublic = "y"
                }
                R.id.rb2 -> {
                    isPublic = "n"
                }
            }
        }

        btnCancel.setOnClickListener { dismiss() }

        btnOk.setOnClickListener {
            if(memo == ""){
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else {
//                val addMemoModel = AddMemoModel(memo, isPublic, bookIsbn)
//                memoViewModel.addMemo(accessToken, addMemoModel)
            }
        }
    }
}