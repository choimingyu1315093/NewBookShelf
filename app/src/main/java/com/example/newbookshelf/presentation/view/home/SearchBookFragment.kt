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
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
import com.example.newbookshelf.data.model.home.searchbook.SearchMoreBookData
import com.example.newbookshelf.data.model.home.searchbook.SearchedBook
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentSearchBookBinding
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchBookTitleAdapter
import com.example.newbookshelf.presentation.view.home.adapter.SearchMoreBookAdapter
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel

class SearchBookFragment : Fragment() {
    private lateinit var binding: FragmentSearchBookBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchBookTitleAdapter: SearchBookTitleAdapter
    private lateinit var searchBookAdapter: SearchBookAdapter
    private lateinit var searchMoreBookAdapter: SearchMoreBookAdapter

    companion object {
        const val TAG = "SearchBookFragment"
    }

    private var pageCount = 1
    private var bookTitle = ""
    private var popularIsbnList = arrayListOf<String>()
    private var popularBookList: MutableList<SearchBookResult> = mutableListOf()
    private var popularBookZero = false

    private var generalBookList: MutableList<SearchBookResult> = mutableListOf()
    private var generalBookZero = false

    private var isSearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).binding.cl.visibility = View.GONE
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
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        homeViewModel = (activity as HomeActivity).homeViewModel
        searchBookTitleAdapter = (activity as HomeActivity).searchBookTitleAdapter
        searchBookTitleAdapter.setOnClickListener {
            homeViewModel.deleteSearchedBook(it)
        }

        searchBookAdapter = (activity as HomeActivity).searchBookAdapter
        searchBookAdapter.setOnItemClickListener {
            val action = SearchBookFragmentDirections.actionSearchBookFragmentToDetailFragment(book = null, searchBook = it)
            findNavController().navigate(action)
        }

        searchMoreBookAdapter = (activity as HomeActivity).searchMoreBookAdapter
        searchMoreBookAdapter.setOnItemClickListener {
            val action = SearchBookFragmentDirections.actionSearchBookFragmentToDetailFragment(book = null, searchBook = it)
            findNavController().navigate(action)
        }

        //검색 기록
        rvSearchBookTitle.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchBookTitleAdapter
        }

        //검색
        rvSearchBook.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = searchBookAdapter
        }

        //검색 더 보기
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
            //알라딘
            if(generalBookList.size == 0){
                val searchMoreBookData = SearchMoreBookData(popularIsbnList, pageCount, bookTitle, "")
                pageCount++
                homeViewModel.getSearchMoreBook(searchMoreBookData)
            //db
            }else {
                pageCount++
                val searchMoreBookData = SearchMoreBookData(popularIsbnList, pageCount, bookTitle, "")
                homeViewModel.getSearchMoreBook(searchMoreBookData)
            }
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.searchBook.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    //db, 추천 검색 결과
                    val popularResult = response.data?.data?.popular_result
                    Log.d(TAG, "observeViewModel: popularResult $popularResult")
                    if(popularResult != null){
                        if(popularResult.isNotEmpty()){
                            popularBookList.clear()
                            popularBookList = popularResult.toMutableList()

                            popularIsbnList.clear()
                            for(i in 0 until popularResult.size){
                                popularIsbnList.add(popularResult[i].book_isbn!!)
                            }

                            txtCurrentBook.visibility = View.VISIBLE
                            txtCurrentBook.text = "추천 검색 결과"
                            rvSearchBook.visibility = View.VISIBLE
                            rvSearchBookTitle.visibility = View.GONE
                            txtDelete.visibility = View.GONE

                            searchBookAdapter.differ.submitList(popularBookList)
                        }else {
                            txtCurrentBook.visibility = View.GONE
                            txtDelete.visibility = View.GONE
                            rvSearchBook.visibility = View.GONE
                            rvSearchBookTitle.visibility = View.GONE
                            popularBookZero = true
                        }
                    }

                    //알라딘 api(더 많은 검색 결과) 단, 추천 검색 결과가 5개 이하면 호출된다
                    val generalResult = response.data?.data?.general_result
                    Log.d(TAG, "observeViewModel: generalResult $generalResult")
                    if(generalResult != null){
                        if(generalResult.isNotEmpty()){
                            generalBookList.clear()
                            generalBookList = generalResult.toMutableList()

                            txtSearchMoreBook.visibility = View.VISIBLE
                            rvSearchMoreBook.visibility = View.VISIBLE
                            searchMoreBookAdapter.differ.submitList(generalBookList)
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
                    progressBar.visibility = View.GONE
                }
                is Resource.Error -> Unit
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }

        homeViewModel.searchMoreBook.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Success -> {
                    if(response.data!!.data.isNotEmpty()){
                        if(generalBookList.size != 0){
                            for(i in 0 until response.data.data.size){
                                generalBookList.add(response.data.data[i])
                            }
                            searchMoreBookAdapter.differ.submitList(generalBookList)
                            rvSearchMoreBook.adapter = searchMoreBookAdapter
                        }else {
                            for(i in 0 until response.data.data.size){
                                popularBookList.add(response.data.data[i])
                            }
                            searchBookAdapter.differ.submitList(popularBookList)
                            rvSearchBook.adapter = searchBookAdapter
                        }
                    }
                    progressBar.visibility = View.GONE
                }
                is Resource.Error -> Unit
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }

        homeViewModel.isEnd.observe(viewLifecycleOwner){ response ->
            if(response){
                btnMore.visibility = View.GONE
            }else {
                if(isSearch){
                    btnMore.visibility = View.VISIBLE
                }else {
                    btnMore.visibility = View.GONE
                }
            }
        }

        homeViewModel.getSearchedBook().observe(viewLifecycleOwner){ response ->
            if(response.isNotEmpty()){
                txtCurrentBook.visibility = View.VISIBLE
                txtDelete.visibility = View.VISIBLE
                rvSearchBookTitle.visibility = View.VISIBLE
                searchBookTitleAdapter.differ.submitList(response)
            }else {
                txtCurrentBook.visibility = View.GONE
                txtDelete.visibility = View.GONE
                rvSearchBookTitle.visibility = View.GONE
            }
        }
    }

    private fun searchKeyword(bookName: String) = with(binding){
        isSearch = true
        bookTitle = bookName
        homeViewModel.getSearchBook(bookName)
        homeViewModel.insertSearchedBook(SearchedBook(title = bookName))
        binding.etSearch.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
        binding.rvSearchBook.visibility = View.GONE
        binding.rvSearchMoreBook.visibility = View.GONE
        binding.btnMore.visibility = View.GONE

        homeViewModel.searchBook.value = null
        homeViewModel.searchMoreBook.value = null
        homeViewModel.isEnd.value = false
        isSearch = false
    }
}