package com.example.newbookshelf.presentation.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
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

    private lateinit var accessToken: String

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
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        homeViewModel = (activity as HomeActivity).homeViewModel
        notificationAdapter = (activity as HomeActivity).notificationAdapter
        notificationAdapter.setOnClickListener {
            homeViewModel.alarmOneDelete(accessToken, it.alarm_idx)
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
            homeViewModel.alarmAllDelete(accessToken)
        }
    }

    private fun observeViewModel() = with(binding){
        homeViewModel.alarmList(accessToken).observe(viewLifecycleOwner){
            if(it.data!!.data.isNotEmpty()){
                notificationAdapter.differ.submitList(it.data.data)
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

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeActivity).binding.cl.visibility = View.VISIBLE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }
}