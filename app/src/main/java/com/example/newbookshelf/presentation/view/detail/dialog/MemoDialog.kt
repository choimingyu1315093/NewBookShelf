package com.example.newbookshelf.presentation.view.detail.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.detail.memo.AddBookMemoData
import com.example.newbookshelf.data.model.detail.memo.UpdateBookMemoData
import com.example.newbookshelf.data.model.detail.review.AddBookReviewData
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentMemoDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel

class MemoDialog(
    private val bookIsbn: String,
    private var update: Boolean,
    private val bookMemoIdx: Int,
    private val description: String,
    private var isPrivate: String,
    private val onDialogCloseListener: OnDialogCloseListener
) : DialogFragment() {

    private lateinit var binding: FragmentMemoDialogBinding
    private lateinit var detailViewModel: DetailViewModel

    interface OnDialogCloseListener {
        fun memoReload(b: Boolean, isUpdate: Boolean)
    }

    companion object {
        const val TAG = "MemoDialog"
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
        return inflater.inflate(R.layout.fragment_memo_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMemoDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        detailViewModel = (activity as HomeActivity).detailViewModel
        if(description != ""){
            etMemo.setText(description)
            if(isPrivate == "n"){
                rb1.isChecked = true
            }else {
                rb2.isChecked = true
            }
        }
    }

    private fun bindViews() = with(binding){
        rg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb1 -> {
                    isPrivate = "n"
                }
                R.id.rb2 -> {
                    isPrivate = "y"
                }
            }
        }

        btnCancel.setOnClickListener { dismiss() }

        btnOk.setOnClickListener {
            if(etMemo.text.toString() == ""){
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else if(update){
                val updateBookMemoData = UpdateBookMemoData(isPrivate, etMemo.text.toString())
                detailViewModel.updateBookMemo(accessToken, bookMemoIdx, updateBookMemoData)
                update = false
            }else {
                val addBookMemoData = AddBookMemoData(bookIsbn, isPrivate, etMemo.text.toString())
                detailViewModel.addBookMemo(accessToken, addBookMemoData)
            }
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.addBookMemoResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    onDialogCloseListener.memoReload(true, false)
                    Log.d(TAG, "observeViewModel: 호출 ${etMemo.text.toString()}, $update")
                    if(etMemo.text.toString() != ""){
                        if(!update){
                            dismiss()
                            etMemo.setText("")
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }

        detailViewModel.updateBookMemoResult.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    onDialogCloseListener.memoReload(true, false)
                    if(etMemo.text.toString() != ""){
                        if(!update){
                            dismiss()
                            etMemo.setText("")
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }
}