package com.example.newbookshelf.presentation.view.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.example.newbookshelf.data.model.signup.SignupData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentInputBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModel

class InputFragment : Fragment() {
    private lateinit var binding: FragmentInputBinding
    lateinit var signupViewModel: SignupViewModel

    companion object {
        const val TAG = "InputFragment"
    }

    private var id = ""
    private var email = ""
    private var password = ""
    private var nickname = ""
    private var fcmToken = ""

    private var idCheck = false
    private var passwordCheck = false
    private var passwordMatchCheck = false
    private var nicknameCheck = false
    private var emailCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init() = with(binding){
        signupViewModel = (activity as SignUpActivity).signupViewModel
        (activity as SignUpActivity).binding.tvTitle.text = "회원 정보 입력"
        (activity as SignUpActivity).binding.ivBack.visibility = View.VISIBLE
        fcmToken = BookShelfApp.prefs.getFcmToken("fcmToken", "")
        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etId.windowToken, 0)
        }
    }

    private fun bindViews() = with(binding){
        (activity as SignUpActivity).binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,15}$")
                if(regex.matches(s.toString())){
                    id = s.toString()
                    signupViewModel.idCheck(id)
                    txtIdWarning.visibility = View.GONE
                }else {
                    id = ""
                    idCheck = false
                    txtIdWarning.visibility = View.VISIBLE
                    txtIdWarning.text = "❗아이디는 영문,숫자 포함하여 5~15자이어야 합니다."
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                if(emailRegex.matches(s.toString())){
                    email = s.toString()
                    val emailCheckData = EmailCheckData(email)
                    signupViewModel.emailCheck(emailCheckData)
                    txtEmailWarning.visibility = View.GONE
                }else {
                    email = ""
                    emailCheck = false
                    txtEmailWarning.visibility = View.VISIBLE
                    txtEmailWarning.text = "❗이메일 형식에 맞지 않습니다."
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,15}$")
                if(passwordPattern.matches(s.toString())){
                    passwordCheck = true
                    password = s.toString()
                    txtPasswordWarning.visibility = View.GONE

                    if(etPasswordCheck.text.toString() != "" && etPasswordCheck.text.toString() != s.toString()){
                        passwordCheck = false
                        txtPasswordCheckWarning.visibility = View.VISIBLE
                    }
                }else {
                    passwordCheck = false
                    password = ""
                    txtPasswordWarning.visibility = View.VISIBLE
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etPasswordCheck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentPassword = etPassword.text.toString()
                if(currentPassword != s.toString()){
                    passwordMatchCheck = false
                    txtPasswordCheckWarning.visibility = View.VISIBLE
                }else {
                    passwordMatchCheck = true
                    txtPasswordCheckWarning.visibility = View.GONE
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString() != ""){
                    nickname = s.toString()
                    signupViewModel.nicknameCheck(nickname)
                }else {
                    nickname = ""
                    nicknameCheck = false
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        btnSignUp.setOnClickListener {
            val signupData = SignupData(fcmToken, "general", email, id, nickname, password)
            signupViewModel.signup(signupData)
        }
    }

    private fun observeViewModel() = with(binding){
        signupViewModel.idCheckResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    idCheck = true
                    txtIdWarning.visibility = View.GONE
                }
                is Resource.Error -> {
                    idCheck = false
                    txtIdWarning.visibility = View.VISIBLE
                    txtIdWarning.text = "❗이미 사용 중인 아이디 입니다."
                }
                is Resource.Loading -> {

                }
            }
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
        }

        signupViewModel.emailCheckResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    emailCheck = true
                    txtEmailWarning.visibility = View.GONE
                }
                is Resource.Error -> {
                    emailCheck = false
                    txtEmailWarning.visibility = View.VISIBLE
                    txtEmailWarning.text = "❗️이미 사용 중인 이메일 입니다."
                }
                is Resource.Loading -> {

                }
            }
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
        }

        signupViewModel.nicknameCheckResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    nicknameCheck = true
                    txtNicknameWarning.visibility = View.GONE
                }
                is Resource.Error -> {
                    nicknameCheck = false
                    txtNicknameWarning.visibility = View.VISIBLE
                }
                is Resource.Loading -> {

                }
            }
            signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
        }

        signupViewModel.signupResult.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_inputFragment_to_successFragment)
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun signUpBtnSetting(idCheck: Boolean, nicknameCheck: Boolean, emailCheck: Boolean, passwordCheck: Boolean, passwordMatchCheck: Boolean) = with(binding){
        if(idCheck && nicknameCheck && emailCheck && passwordCheck && passwordMatchCheck){
            btnSignUp.setBackgroundResource(R.drawable.btn_main_no_10)
            btnSignUp.isEnabled = true
        }else {
            btnSignUp.setBackgroundResource(R.drawable.btn_e9e9e9_10)
            btnSignUp.isEnabled = false
        }
    }
}