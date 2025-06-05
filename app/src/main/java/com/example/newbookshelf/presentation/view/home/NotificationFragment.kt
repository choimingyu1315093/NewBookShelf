package com.example.newbookshelf.presentation.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentNotificationBinding
import com.example.newbookshelf.presentation.view.home.adapter.NotificationAdapter
import com.example.newbookshelf.presentation.viewmodel.home.HomeViewModel

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var notificationAdapter: NotificationAdapter

    companion object {
        const val TAG = "NotificationFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        homeViewModel = (activity as HomeActivity).homeViewModel
        notificationAdapter = (activity as HomeActivity).notificationAdapter
        homeViewModel.alarmList()
        notificationAdapter.setOnClickListener {
            homeViewModel.alarmOneDelete(it.alarm_idx)
        }

        rvNotification.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = notificationAdapter
        }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        txtClear.setOnClickListener {
            homeViewModel.alarmAllDelete()
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.alarmListResult.observe(viewLifecycleOwner){
            it.data?.let {
                if(it.data.isNotEmpty()){
                    notificationAdapter.differ.submitList(it.data)
                    rvNotification.visibility = View.VISIBLE
                    txtClear.visibility = View.VISIBLE
                    txtEmptyNotification.visibility = View.GONE
                    ivEmptyNotification.visibility = View.GONE
                }else {
                    rvNotification.visibility = View.GONE
                    txtClear.visibility = View.GONE
                    txtEmptyNotification.visibility = View.VISIBLE
                    ivEmptyNotification.visibility = View.VISIBLE
                }
            }
        }

        homeViewModel.alarmAllDeleteResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    homeViewModel.alarmList()
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                else -> Unit
            }
        }

        homeViewModel.alarmOneDeleteResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    homeViewModel.alarmList()
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                else -> Unit
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }
}