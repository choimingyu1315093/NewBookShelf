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
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.detail.review.AddBookReviewData
import com.example.newbookshelf.data.model.detail.review.UpdateBookReviewData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentReviewDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel

class ReviewDialog(
    private val bookIsbn: String,
    private var update: Boolean,
    private val bookCommentIdx: Int,
    private val description: String,
    private val rating: Double,
    private val onDialogCloseListener: OnDialogCloseListener
) : DialogFragment() {

    private lateinit var binding: FragmentReviewDialogBinding
    private lateinit var detailViewModel: DetailViewModel

    interface OnDialogCloseListener {
        fun commentReload(b: Boolean, isUpdate: Boolean)
    }

    companion object {
        const val TAG = "ReviewDialog"
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
        return inflater.inflate(R.layout.fragment_review_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        detailViewModel = (activity as HomeActivity).detailViewModel
        if(description != ""){
            etReview.setText(description)
            rb.rating = rating.toFloat()
        }
    }

    private fun bindViews() = with(binding){
        btnCancel.setOnClickListener { dismiss() }

        btnOk.setOnClickListener {
            if(etReview.text.toString() == ""){
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else if(update){
                val updateBookReviewData = UpdateBookReviewData(etReview.text.toString(), rb.rating.toDouble())
                detailViewModel.updateBookReview(bookCommentIdx, updateBookReviewData)
                update = false
            }else {
                val addBookReviewData = AddBookReviewData(bookIsbn, etReview.text.toString(), rb.rating.toDouble())
                detailViewModel.addBookReview(addBookReviewData)
            }
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.addBookReviewResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    onDialogCloseListener.commentReload(true, false)
                    if(etReview.text.toString() != ""){
                        if(!update){
                            dismiss()
                            etReview.setText("")
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                else -> Unit
            }
        }

        detailViewModel.updateBookReviewResult.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    onDialogCloseListener.commentReload(true, true)
                    if(etReview.text.toString() != ""){
                        if(!update){
                            dismiss()
                            etReview.setText("")
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                else -> Unit
            }
        }
    }
}