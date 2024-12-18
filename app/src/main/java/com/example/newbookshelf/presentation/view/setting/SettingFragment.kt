package com.example.newbookshelf.presentation.view.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.setting.UpdateUserSettingData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentSettingBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.login.LoginActivity
import com.example.newbookshelf.presentation.viewmodel.setting.SettingViewModel

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var settingViewModel: SettingViewModel

    companion object {
        const val TAG = "SettingFragment"
    }

    private lateinit var accessToken: String
    private var ticket = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        (activity as HomeActivity).binding.cl.visibility = View.GONE
        (activity as HomeActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        init()
        bindViews()
    }

    private fun init() = with(binding){
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        settingViewModel = (activity as HomeActivity).settingViewModel
        settingViewModel.userSetting(accessToken).observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        ticket = it.data.ticket_count
                        tvWelcome.text = "${it.data.user_name} 님, 환영합니다."
                        btnMoney.text = "${it.data.ticket_count}장"
                        swChat.isChecked = it.data.setting_chat_alarm == 1
                        swMarketing.isChecked = it.data.setting_marketing_alarm == 1
                        swNear.isChecked = it.data.setting_wish_book_alarm == 1
                        swChatRequest.isChecked = it.data.setting_chat_receive == 1
                        progressBar.visibility = View.GONE
                    }
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
            }
        }
    }

    private fun bindViews() = with(binding){
        setupSwitchListener(swChat, "setting_chat_alarm")
        setupSwitchListener(swMarketing, "setting_marketing_alarm")
        setupSwitchListener(swNear, "setting_wish_book_alarm")
        setupSwitchListener(swChatRequest, "setting_chat_receive")

        cl3.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("ticket", ticket)
            }
            findNavController().navigate(R.id.action_settingFragment_to_chargeFragment, bundle)
        }

        cl4.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_settingFragment_to_chargeLogFragment)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl5.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_settingFragment_to_passwordChangeFragment)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl6.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_settingFragment_to_secessionFragment)
            (requireActivity() as HomeActivity).binding.bottomNavigationView.visibility = View.GONE
        }

        cl7.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            (activity as HomeActivity).finish()
            BookShelfApp.prefs.setAutoLogin("autoLogin", false)
            BookShelfApp.prefs.setLoginId("id", "")
            BookShelfApp.prefs.setLoginPw("password", "")
        }
    }

    private fun setupSwitchListener(switch: SwitchCompat, settingKey: String) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            val updateUserSettingData = UpdateUserSettingData(settingKey, isChecked)
            settingViewModel.updateUserSetting(accessToken, updateUserSettingData)
        }
    }
}