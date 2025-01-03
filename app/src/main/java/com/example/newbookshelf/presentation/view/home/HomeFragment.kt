package com.example.newbookshelf.presentation.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentHomeBinding
import com.example.newbookshelf.presentation.view.home.adapter.AttentionBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.DetailAttentionBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.DetailNewBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.DetailWeekBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.NewBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.adapter.WeekBestsellerAdapter
import com.example.newbookshelf.presentation.view.home.dialog.BestsellerFilterDialog
import com.example.newbookshelf.presentation.view.login.LoginActivity
import com.example.newbookshelf.presentation.viewmodel.detail.DetailViewModel
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel
import com.example.newbookshelf.presentation.viewmodel.profile.ProfileViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), BestsellerFilterDialog.OnApplyListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        const val TAG = "HomeFragment"
    }

    private lateinit var weekBestsellerAdapter: WeekBestsellerAdapter
    private lateinit var newBestsellerAdapter: NewBestsellerAdapter
    private lateinit var attentionBestsellerAdapter: AttentionBestsellerAdapter
    private lateinit var detailWeekBestsellerAdapter: DetailWeekBestsellerAdapter
    private lateinit var detailNewBestsellerAdapter: DetailNewBestsellerAdapter
    private lateinit var detailAttentionBestsellerAdapter: DetailAttentionBestsellerAdapter

    private var searchTarget = BookShelfApp.prefs.getSearchTarget("searchTarget", "Book")
    private var categoryId = BookShelfApp.prefs.getCategoryId("categoryId", 0)

    private var newSearchTarget = BookShelfApp.prefs.getNewSearchTarget("newSearchTarget", "Book")
    private var newCategoryId = BookShelfApp.prefs.getNewCategoryId("newCategoryId", 0)

    private var attentionSearchTarget = BookShelfApp.prefs.getAttentionSearchTarget("attentionSearchTarget", "Book")
    private var attentionCategoryId = BookShelfApp.prefs.getAttentionCategoryId("attentionCategoryId", 0)

    private var type = "week"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        homeViewModel = (activity as HomeActivity).homeViewModel
        detailViewModel = (activity as HomeActivity).detailViewModel
        profileViewModel = (activity as HomeActivity).profileViewModel

        weekBestsellerAdapter = (activity as HomeActivity).weekBestsellerAdapter
        weekBestsellerAdapter.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(book = it, searchBook = null)
            findNavController().navigate(action)
        }

        newBestsellerAdapter = (activity as HomeActivity).newBestsellerAdapter
        newBestsellerAdapter.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(book = it, searchBook = null)
            findNavController().navigate(action)
        }

        attentionBestsellerAdapter = (activity as HomeActivity).attentionBestseller
        attentionBestsellerAdapter.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(book = it, searchBook = null)
            findNavController().navigate(action)
        }

        detailWeekBestsellerAdapter = (activity as HomeActivity).detailWeekBestsellerAdapter
        detailNewBestsellerAdapter = (activity as HomeActivity).detailNewBestsellerAdapter
        detailAttentionBestsellerAdapter = (activity as HomeActivity).detailAttentionBestsellerAdapter

        rvWeekBestseller.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = weekBestsellerAdapter
        }

        rvNewBestseller.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = newBestsellerAdapter
        }

        rvAttentionBestseller.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = attentionBestsellerAdapter
        }

        rvDetailWeekBestseller.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = detailWeekBestsellerAdapter
        }

        rvDetailNewBestseller.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = detailNewBestsellerAdapter
        }

        rvDetailAttentionBestseller.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = detailAttentionBestsellerAdapter
        }
    }

    private fun bindViews() = with(binding){
        ivWeekFront.setOnClickListener {
            type = "week"
            txtDetailWeekBest.text = "이번 주 베스트셀러"
            showBestsellerView(weekVisible = true, newVisible = false, attentionVisible = false)
        }

        ivNewFront.setOnClickListener {
            type = "new"
            txtDetailWeekBest.text = "신간 도서 목록"
            showBestsellerView(weekVisible = false, newVisible = true, attentionVisible = false)
        }

        ivAttentionFront.setOnClickListener {
            type = "attention"
            txtDetailWeekBest.text = "주목할 만한 신간 목록"
            showBestsellerView(weekVisible = false, newVisible = false, attentionVisible = true)
        }

        ivBestsellerSearch.setOnClickListener {
            val dialog = BestsellerFilterDialog(this@HomeFragment, type)
            dialog.show(requireActivity().supportFragmentManager, "BestsellerFilterDialog")
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.weekBestseller(searchTarget, categoryId).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let {
                        weekBestsellerAdapter.isDetail = false
                        weekBestsellerAdapter.differ.submitList(it.item)
                        detailWeekBestsellerAdapter.differ.submitList(it.item)
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        }

        homeViewModel.newBestseller(newSearchTarget, newCategoryId).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let {
                        newBestsellerAdapter.isDetail = false
                        newBestsellerAdapter.differ.submitList(it.item)
                        detailNewBestsellerAdapter.differ.submitList(it.item)
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        }

        homeViewModel.attentionBestseller(attentionSearchTarget, attentionCategoryId).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let {
                        attentionBestsellerAdapter.isDetail = false
                        attentionBestsellerAdapter.differ.submitList(it.item)
                        detailAttentionBestsellerAdapter.differ.submitList(it.item)
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        }

        homeViewModel.isDetail.observe(viewLifecycleOwner){
            if(it){
                cl3.visibility = View.GONE
                cl4.visibility = View.VISIBLE
            }else {
                cl3.visibility = View.VISIBLE
                cl4.visibility = View.GONE
            }
        }

        profileViewModel.myProfile().observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        BookShelfApp.prefs.setUserIdx("userIdx", it.data.user_idx)
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    private fun showProgress() = with(binding){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() = with(binding){
        progressBar.visibility = View.GONE
    }

    private fun showBestsellerView(weekVisible: Boolean, newVisible: Boolean, attentionVisible: Boolean) = with(binding) {
        rvDetailWeekBestseller.visibility = if (weekVisible) View.VISIBLE else View.GONE
        rvDetailNewBestseller.visibility = if (newVisible) View.VISIBLE else View.GONE
        rvDetailAttentionBestseller.visibility = if (attentionVisible) View.VISIBLE else View.GONE
        homeViewModel.setDetail(true)
    }

    override fun next(b: Boolean, type: String) {
        if(type == "week"){
            searchTarget = BookShelfApp.prefs.getSearchTarget("searchTarget", "Book")
            categoryId = BookShelfApp.prefs.getCategoryId("categoryId", 0)
            homeViewModel.weekBestseller(searchTarget, categoryId).observe(viewLifecycleOwner){ response ->
                when(response){
                    is Resource.Success -> {
                        hideProgress()
                        response.data?.let {
                            weekBestsellerAdapter.isDetail = false
                            weekBestsellerAdapter.differ.submitList(it.item)
                            detailWeekBestsellerAdapter.differ.submitList(it.item)
                        }
                    }
                    is Resource.Error -> {
                        hideProgress()
                    }
                    is Resource.Loading -> {
                        showProgress()
                    }
                }
            }
        }else if(type == "new"){
            newSearchTarget = BookShelfApp.prefs.getNewSearchTarget("newSearchTarget", "Book")
            newCategoryId = BookShelfApp.prefs.getNewCategoryId("newCategoryId", 0)
            homeViewModel.newBestseller(newSearchTarget, newCategoryId).observe(viewLifecycleOwner){ response ->
                when(response){
                    is Resource.Success -> {
                        hideProgress()
                        response.data?.let {
                            newBestsellerAdapter.isDetail = false
                            newBestsellerAdapter.differ.submitList(it.item)
                            detailNewBestsellerAdapter.differ.submitList(it.item)
                        }
                    }
                    is Resource.Error -> {
                        hideProgress()
                    }
                    is Resource.Loading -> {
                        showProgress()
                    }
                }
            }
        }else {
            attentionSearchTarget = BookShelfApp.prefs.getAttentionSearchTarget("attentionSearchTarget", "Book")
            attentionCategoryId = BookShelfApp.prefs.getAttentionCategoryId("attentionCategoryId", 0)
            homeViewModel.attentionBestseller(attentionSearchTarget, attentionCategoryId).observe(viewLifecycleOwner){ response ->
                when(response){
                    is Resource.Success -> {
                        hideProgress()
                        response.data?.let {
                            attentionBestsellerAdapter.isDetail = false
                            attentionBestsellerAdapter.differ.submitList(it.item)
                            detailAttentionBestsellerAdapter.differ.submitList(it.item)
                        }
                    }
                    is Resource.Error -> {
                        hideProgress()
                    }
                    is Resource.Loading -> {
                        showProgress()
                    }
                }
            }
        }
    }
}