package com.example.newbookshelf.presentation.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentChargeLogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.setting.adapter.ChargeLogAdapter
import com.example.newbookshelf.presentation.viewmodel.setting.SettingViewModel

class ChargeLogFragment : Fragment() {
    private lateinit var binding: FragmentChargeLogBinding
    private lateinit var settingViewModel: SettingViewModel

    companion object {
        const val TAG = "ChargeLogFragment"
    }

    private lateinit var accessToken: String
    private lateinit var chargeLogAdapter: ChargeLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_charge_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChargeLogBinding.bind(view)

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        chargeLogAdapter = (activity as HomeActivity).chargeLogAdapter
        rvCharge.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = chargeLogAdapter
        }
        settingViewModel = (activity as HomeActivity).settingViewModel
        settingViewModel.ticketLog(accessToken).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        if(response.data.data.isNotEmpty()){
                            clEmpty.visibility = View.GONE
                            rvCharge.visibility = View.VISIBLE
                            chargeLogAdapter.differ.submitList(response.data.data)
                        }else {
                            clEmpty.visibility = View.VISIBLE
                            rvCharge.visibility = View.GONE
                        }
                        progressBar.visibility = View.GONE
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}