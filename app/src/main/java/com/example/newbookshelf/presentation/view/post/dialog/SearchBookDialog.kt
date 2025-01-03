package com.example.newbookshelf.presentation.view.post.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentSearchBookDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookAdapter
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel

class SearchBookDialog(private val onSelectedBook: OnSelectedBook) : DialogFragment() {
    private lateinit var binding: FragmentSearchBookDialogBinding
    private lateinit var homeViewModel: HomeViewModel

    interface OnSelectedBook {
        fun onSelectedBook(book: String)
    }

    companion object {
        const val TAG = "SearchBookDialog"
    }

    private lateinit var accessToken: String
    private lateinit var searchBookAdapter: SearchBookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_book_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBookDialogBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        homeViewModel = (activity as HomeActivity).homeViewModel
        searchBookAdapter = (activity as HomeActivity).searchBookAdapter
        searchBookAdapter.setOnItemClickListener {
            dismiss()
            onSelectedBook.onSelectedBook(it.book_name!!)
        }
        rvBook.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchBookAdapter
        }
    }

    private fun bindViews() = with(binding){
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                searchKeyword(etSearch.text.toString().trim())
                val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(etSearch.windowToken, 0)
                view?.clearFocus()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        ivSearch.setOnClickListener {
            searchKeyword(etSearch.text.toString().trim())
            val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(etSearch.windowToken, 0)
            view?.clearFocus()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun searchKeyword(book: String) = with(binding){
        homeViewModel.getSearchBook(book)
//        postViewModel.searchPlace(kakaoKey, place).observe(viewLifecycleOwner){ response ->
//            when(response){
//                is Resource.Success -> {
//                    response.data?.let {
//                        if(it.documents.isNotEmpty()){
//                            txtEmpty.visibility = View.GONE
//                            rvPlace.visibility = View.VISIBLE
//                            kakaoAdapter.differ.submitList(it.documents)
//                        }else {
//                            txtEmpty.visibility = View.VISIBLE
//                            rvPlace.visibility = View.GONE
//                        }
//                    }
//                }
//                is Resource.Error -> Unit
//                is Resource.Loading -> Unit
//            }
//        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.searchBook.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        if(it.data.general_result?.size != 0){
                            txtEmpty.visibility = View.GONE
                            rvBook.visibility = View.VISIBLE
                            searchBookAdapter.differ.submitList(it.data.general_result)
                        }else {
                            txtEmpty.visibility = View.VISIBLE
                            rvBook.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }
}