package com.example.newbookshelf.presentation.view.detail

import android.os.Bundle
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
        rvReview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = reviewAdapter
        }
        rb.setIsIndicator(true)
    }

    private fun bindViews() = with(binding){
        btnWrite.setOnClickListener {
            val dialog = ReviewDialog(isbn, this@ReviewFragment)
            dialog.show(requireActivity().supportFragmentManager, "ReviewDialog")
        }
    }

    private fun observeViewModel() = with(binding){
        detailViewModel.detailBookResult.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    val book = response.data?.data
                    rb.rating = book!!.book_average_rate.toFloat()
                    val percent = book.book_average_rate
//                    val roundedPercent = round(percent).toInt()
//                    tvAverage.text = roundedPercent.toString()
                    tvAverage.text = book.book_average_rate.toString()
                    if(book.book_comments.size != 0){
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
    }

    override fun commentReload(b: Boolean) {
        if(b){
            detailViewModel.detailBook(accessToken, isbn)
        }
    }
}