package com.example.newbookshelf.presentation.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentSecessionBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.setting.SettingViewModel

class SecessionFragment : Fragment() {
    private lateinit var binding: FragmentSecessionBinding
    private lateinit var settingViewModel: SettingViewModel

    companion object {
        const val TAG = "SecessionFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_secession, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecessionBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        settingViewModel = (activity as HomeActivity).settingViewModel
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnOk.setOnClickListener {
            settingViewModel.userDelete()
            BookShelfApp.prefs.setAutoLogin("autoLogin", false)
            BookShelfApp.prefs.setLoginId("id", "")
            BookShelfApp.prefs.setLoginPw("password", "")
        }
    }

    private fun observeViewModel() = with(binding){
        settingViewModel.userDeleteResult.observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_secessionFragment_to_secessionSuccessFragment)
                }
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                else -> Unit
            }
        }
    }
}