package com.example.newbookshelf.presentation.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentReviewBinding
import com.example.newbookshelf.presentation.view.detail.adapter.MemoAdapter
import com.example.newbookshelf.presentation.view.detail.adapter.ReviewAdapter
import com.example.newbookshelf.presentation.view.detail.dialog.ReviewDialog
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel
import kotlin.math.round

class ReviewFragment(private val isbn: String) : Fragment(), ReviewDialog.OnDialogCloseListener {
    private lateinit var binding: FragmentReviewBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var reviewAdapter: ReviewAdapter

    companion object {
        const val TAG = "ReviewFragment"
    }

    private lateinit var accessToken: String
    private var isDelete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        detailViewModel = (activity as HomeActivity).detailViewModel
        detailViewModel.detailBook(accessToken, isbn)
        reviewAdapter = (activity as HomeActivity).reviewAdapter
        reviewAdapter.setOnUpdateListener {
            val dialog = ReviewDialog(isbn, true, it.book_comment_idx ,it.comment_content, it.comment_rate, this@ReviewFragment)
            dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
        }
        reviewAdapter.setOnDeleteListener {
            isDelete = true
            detailViewModel.deleteBookReview(accessToken, it.book_comment_idx)
        }
        rvReview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = reviewAdapter
        }
        rb.setIsIndicator(true)
    }

    private fun bindViews() = with(binding){
        btnWrite.setOnClickListener {
            val dialog = ReviewDialog(isbn, false, 0,"", 0.0, this@ReviewFragment)
            dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.detailBookResult.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    val book = response.data?.data
                    if(book!!.book_average_rate != null){
                        rb.rating = book.book_average_rate!!.toFloat()
                        tvAverage.text = book.book_average_rate.toString()
                    }else {
                        tvAverage.text = "0.0"
                    }

                    if(book.book_comments?.size != null && book.book_comments.size > 0){
                        rvReview.visibility = View.VISIBLE
                        tvEmpty.visibility = View.GONE

                        reviewAdapter.differ.submitList(book.book_comments)
                    }else {
                        rvReview.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }

        detailViewModel.deleteBookReviewResult.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    if(isDelete){
                        detailViewModel.detailBook(accessToken, isbn)
                        Toast.makeText(activity, "후기를 삭제했습니다.", Toast.LENGTH_SHORT).show()
                        isDelete = false
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    override fun commentReload(b: Boolean, isUpdate: Boolean) {
        if(b){
            detailViewModel.detailBook(accessToken, isbn)
            if(isUpdate){
                Toast.makeText(activity, "후기를 수정했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}