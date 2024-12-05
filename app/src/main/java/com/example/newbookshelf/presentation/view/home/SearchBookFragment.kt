package com.example.newbookshelf.presentation.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.databinding.FragmentSearchBookBinding
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookTitleAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchMoreBookAdapter
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class SearchBookFragment : Fragment() {
    private lateinit var binding: FragmentSearchBookBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchBookTitleAdapter: SearchBookTitleAdapter
    private lateinit var searchBookAdapter: SearchBookAdapter
    private lateinit var searchMoreBookAdapter: SearchMoreBookAdapter

    companion object {
        const val TAG = "SearchBookFragment"
    }

    private lateinit var accessToken: String
    private var bookTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBookBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        homeViewModel = (activity as HomeActivity).homeViewModel
        searchBookTitleAdapter = (activity as HomeActivity).searchBookTitleAdapter
        searchBookAdapter = (activity as HomeActivity).searchBookAdapter
        searchMoreBookAdapter = (activity as HomeActivity).searchMoreBookAdapter

        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")

        rvSearchBookTitle.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchBookTitleAdapter
        }

        rvSearchBook.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchBookAdapter
        }

        rvSearchMoreBook.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchMoreBookAdapter
        }

        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etSearch.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
//                isSearch = true
                searchKeyword(etSearch.text.toString().trim())
                val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(etSearch.windowToken, 0)
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        txtDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                homeViewModel.allDeleteSearchedBook()
//                withContext(Dispatchers.Main) {
//                    rvSearchBookTitle.visibility = View.GONE
//                    txtCurrentBook.visibility = View.GONE
//                    txtDelete.visibility = View.GONE
//                }
            }
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.getSearchBook(accessToken, bookTitle).observe(viewLifecycleOwner){ response ->

        }

        homeViewModel.getSearchedBook().observe(viewLifecycleOwner){ response ->
            Log.d(TAG, "observeViewModel: response $response")
            if(response.isNotEmpty()){
                rvSearchBookTitle.visibility = View.VISIBLE
                txtCurrentBook.visibility = View.VISIBLE
                txtDelete.visibility = View.VISIBLE
                searchBookTitleAdapter.differ.submitList(response)
            }else {
                rvSearchBookTitle.visibility = View.GONE
                txtCurrentBook.visibility = View.GONE
                txtDelete.visibility = View.GONE
            }
        }
    }

    private fun searchKeyword(bookName: String){
        bookTitle = bookName
        homeViewModel.getSearchBook(accessToken, bookName)
        homeViewModel.insertSearchedBook(SearchedBook(title = bookName))
        binding.etSearch.setText("")
    }
}