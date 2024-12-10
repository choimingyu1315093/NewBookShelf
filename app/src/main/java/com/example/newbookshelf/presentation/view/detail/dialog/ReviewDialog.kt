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
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentReviewDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel

class ReviewDialog(private val bookIsbn: String, private val onDialogCloseListener: OnDialogCloseListener) : DialogFragment() {
    private lateinit var binding: FragmentReviewDialogBinding
    private lateinit var detailViewModel: DetailViewModel

    interface OnDialogCloseListener {
        fun commentReload(b: Boolean)
    }

    companion object {
        const val TAG = "ReviewDialog"
    }

    private lateinit var accessToken: String
    private var comment = ""

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
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        detailViewModel = (activity as HomeActivity).detailViewModel
    }

    private fun bindViews() = with(binding){
        etReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                comment = s.toString().trim()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        btnCancel.setOnClickListener { dismiss() }

        btnOk.setOnClickListener {
            if(comment == ""){
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else {
                val addBookReviewData = AddBookReviewData(bookIsbn, comment, rb.rating.toDouble())
                detailViewModel.addBookReview(accessToken, addBookReviewData)
            }
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.addBookReviewResult.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    dismiss()
                    onDialogCloseListener.commentReload(true)
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }
}