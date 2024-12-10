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
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
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
import kotlin.math.log

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

    private var pageCount = 1
    private var bookTitle = ""
    private var popularIsbnList = arrayListOf<String>()
    private var popularBookZero = false

    private var generalBookList: MutableList<SearchBookResult> = mutableListOf()
    private var generalBookZero = false

    private var isSearch = false
    private var addBook = false

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
        searchBookTitleAdapter.setOnClickListener {
            homeViewModel.deleteSearchedBook(it)
        }

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
                isSearch = true
                searchKeyword(etSearch.text.toString().trim())
                val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(etSearch.windowToken, 0)
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        txtDelete.setOnClickListener {
            homeViewModel.allDeleteSearchedBook()
        }

        btnMore.setOnClickListener {
//            if(generalBookList.size == 0){
//                addBook = true
//                val searchBookMoreModel = SearchBookMoreModel(popularIsbnList, pageCount, bookTitle, "")
//                homeViewModel.getSearchBookMoreList(accessToken, searchBookMoreModel)
//                pageCount++
//            }else {
//                addBook = true
//                pageCount++
//                val searchBookMoreModel = SearchBookMoreModel(popularIsbnList, pageCount, bookTitle, "")
//                homeViewModel.getSearchBookMoreList(accessToken, searchBookMoreModel)
//            }
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.searchBook.observe(viewLifecycleOwner){ response ->
            val popularResult = response.data?.data?.popular_result
            if(popularResult != null){
                if(popularResult.isNotEmpty()){
                    popularIsbnList.clear()
                    for(i in 0 until popularResult.size){
                        popularIsbnList.add(popularResult[i].book_isbn!!)
                    }

                    txtSearchBook.visibility = View.VISIBLE
                    txtCurrentBook.visibility = View.VISIBLE
                    rvSearchBook.visibility = View.VISIBLE

                    searchBookAdapter.differ.submitList(popularResult)
                    txtSearchMoreBook.text = "더 많은 검색 결과"
                }else {
                    popularBookZero = true
                    txtSearchMoreBook.text = "추천 검색 결과"
                    txtCurrentBook.visibility = View.GONE
                    txtSearchBook.visibility = View.GONE
                    rvSearchBook.visibility = View.GONE
                    rvSearchBookTitle.visibility = View.GONE
                    txtDelete.visibility = View.GONE
                }
            }

            val generalResult = response.data?.data?.general_result
            if(generalResult != null){
                if(generalResult.isNotEmpty()){
                    if(isSearch){
                        generalBookList.clear()
                        isSearch = false

                        generalBookList = generalResult.toMutableList()

                        txtSearchMoreBook.visibility = View.VISIBLE
                        rvSearchMoreBook.visibility = View.VISIBLE
                        searchMoreBookAdapter.differ.submitList(generalBookList)
                    }else {
                        rvSearchBook.visibility = View.GONE
                        btnMore.visibility = View.GONE
                    }
                }else {
                    generalBookZero = true
                    if(popularBookZero && generalBookZero && isSearch){
                        Toast.makeText(requireContext(), "검색 결과가 없습니다(일단 Toast)", Toast.LENGTH_SHORT).show()
                        isSearch = false
                    }
                    txtSearchMoreBook.visibility = View.GONE
                    rvSearchMoreBook.visibility = View.GONE
                }
            }
        }

        homeViewModel.getSearchedBook().observe(viewLifecycleOwner){ response ->
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

    override fun onDestroyView() {
        super.onDestroyView()

        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
        binding.rvSearchBook.visibility = View.GONE
        binding.rvSearchMoreBook.visibility = View.GONE
    }
}