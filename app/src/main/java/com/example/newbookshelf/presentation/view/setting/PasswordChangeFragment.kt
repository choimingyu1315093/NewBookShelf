package com.example.newbookshelf.presentation.view.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.setting.PasswordChangeData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentPasswordChangeBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.setting.SettingViewModel

class PasswordChangeFragment : Fragment() {
    private lateinit var binding: FragmentPasswordChangeBinding
    private lateinit var settingViewModel: SettingViewModel

    companion object {
        const val TAG = "PasswordChangeFragment"
    }

    private var isCurrent = false
    private var isNew = false
    private var isCheck = false

    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_password_change, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordChangeBinding.bind(view)

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

        etCurrentPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isCurrent = s.toString() != ""
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etNewPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,15}$")
                if(passwordPattern.matches(s.toString())){
                    isNew = true
                    txtNewWarning.visibility = View.GONE
                }else {
                    isNew = false
                    txtNewWarning.visibility = View.VISIBLE
                }
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etCheckPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(etNewPw.text.toString() == s.toString()){
                    isCheck = true
                    txtCheckWarning.visibility = View.GONE
                }else {
                    isCheck = false
                    txtCheckWarning.visibility = View.VISIBLE
                }
                checkNextButtonEnable()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        btnChange.setOnClickListener {
            isClicked = true
            val passwordChangeData = PasswordChangeData(etCurrentPw.text.toString(), etCheckPw.text.toString())
            settingViewModel.passwordChange(passwordChangeData)
        }
    }

    private fun observeViewModel() = with(binding){
        settingViewModel.passwordChangeResult.observe(viewLifecycleOwner){ response ->
            if(isClicked){
                when(response){
                    is Resource.Success -> {
                        findNavController().navigate(R.id.action_passwordChangeFragment_to_passwordChangeSuccessFragment)
                        BookShelfApp.prefs.setLoginPw("password", etCheckPw.text.toString())
                        isClicked = false
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "잘몬된 비밀번호입니다.", Toast.LENGTH_SHORT).show()
                        isClicked = false
                    }
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun checkNextButtonEnable() = with(binding) {
        if(isCurrent && isNew && isCheck){
            btnChange.isEnabled = true
            btnChange.setBackgroundResource(R.drawable.btn_main_no_10)
        }else {
            btnChange.isEnabled = false
            btnChange.setBackgroundResource(R.drawable.btn_e9e9e9_no_10)
        }
    }
}