package com.example.newbookshelf.presentation.view.signup

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.example.newbookshelf.data.model.signup.SignupData
import com.example.newbookshelf.data.util.EditTextCustom.textChange
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentInputBinding
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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

        viewLifecycleOwner.lifecycleScope.launch {
            etId.textChange()
                .debounce(500) //0.5초 동안 입력이 없으면 동작한다.
                .map { it.toString() } //CharSequence -> String으로 변환
                .distinctUntilChanged() //같은 값이 연속으로 오면 무시
                .collectLatest { input ->
                    val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,15}$")
                    if(regex.matches(input)){
                        id = input
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
        }

        viewLifecycleOwner.lifecycleScope.launch {
            etEmail.textChange()
                .debounce(500)
                .map { it.toString() }
                .distinctUntilChanged()
                .collectLatest { input ->
                    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                    if(emailRegex.matches(input)){
                        email = input
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
        }

        viewLifecycleOwner.lifecycleScope.launch {
            etPassword.textChange()
                .debounce(500)
                .map { it.toString() }
                .distinctUntilChanged()
                .collectLatest { input ->
                    val passwordPattern = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,15}$")
                    if(passwordPattern.matches(input)){
                        passwordCheck = true
                        password = input
                        txtPasswordWarning.visibility = View.GONE

                        if(etPasswordCheck.text.toString() != "" && etPasswordCheck.text.toString() != input){
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
        }

        viewLifecycleOwner.lifecycleScope.launch {
            etPasswordCheck.textChange()
                .debounce(500)
                .map { it.toString() }
                .distinctUntilChanged()
                .collectLatest { input ->
                    val currentPassword = etPassword.text.toString()
                    if(currentPassword != input){
                        passwordMatchCheck = false
                        txtPasswordCheckWarning.visibility = View.VISIBLE
                    }else {
                        passwordMatchCheck = true
                        txtPasswordCheckWarning.visibility = View.GONE
                    }
                    signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            etNickname.textChange()
                .debounce(500)
                .map { it.toString() }
                .distinctUntilChanged()
                .collectLatest { input ->
                    if(input != ""){
                        nickname = input
                        signupViewModel.nicknameCheck(nickname)
                    }else {
                        nickname = ""
                        nicknameCheck = false
                    }
                    signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
                }
        }

        btnSignUp.setOnClickListener {
            val signupData = SignupData(fcmToken, "general", email, id, nickname, password)
            signupViewModel.signup(signupData)
        }
    }

    private fun observeViewModel() = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            signupViewModel.idCheckResult.collect { response ->
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
                    is Resource.Loading -> Unit
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            signupViewModel.emailCheckResult.collect { response ->
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
                    is Resource.Loading -> Unit
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            signupViewModel.nicknameCheckResult.collect { response ->
                when(response){
                    is Resource.Success -> {
                        nicknameCheck = true
                        txtNicknameWarning.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        nicknameCheck = false
                        txtNicknameWarning.visibility = View.VISIBLE
                    }
                    is Resource.Loading -> Unit
                }
                signUpBtnSetting(idCheck, nicknameCheck, emailCheck, passwordCheck, passwordMatchCheck)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            signupViewModel.signupResult.collect { response ->
                when(response){
                    is Resource.Success -> {
                        findNavController().navigate(R.id.action_inputFragment_to_successFragment)
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
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