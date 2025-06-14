package com.example.newbookshelf.presentation.view.signup

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
                .debounce(500)
                .map { it.toString() }
                .distinctUntilChanged()
                .collectLatest { input ->
                    val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,15}$")
                    if(regex.matches(input)){
                        signupViewModel.updateInput("id", input)
                        signupViewModel.idCheck(input)
                        txtIdWarning.visibility = View.GONE
                    }else {
                        signupViewModel.updateInput("id", "")
                        signupViewModel.setSignUpCheck("idCheck", true)
                        txtIdWarning.visibility = View.VISIBLE
                        txtIdWarning.text = "❗아이디는 영문,숫자 포함하여 5~15자이어야 합니다."
                    }
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
                        signupViewModel.updateInput("email", input)
                        val emailCheckData = EmailCheckData(input)
                        signupViewModel.emailCheck(emailCheckData)
                        txtEmailWarning.visibility = View.GONE
                    }else {
                        signupViewModel.updateInput("email", "")
                        signupViewModel.setSignUpCheck("emailCheck", false)
                        txtEmailWarning.visibility = View.VISIBLE
                        txtEmailWarning.text = "❗이메일 형식에 맞지 않습니다."
                    }
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
                        signupViewModel.setSignUpCheck("passwordCheck", true)
                        signupViewModel.updateInput("password", input)
                        txtPasswordWarning.visibility = View.GONE
                        if(etPasswordCheck.text.toString() != "" && etPasswordCheck.text.toString() != input){
                            signupViewModel.setSignUpCheck("passwordCheck", false)
                            signupViewModel.setSignUpCheck("passwordMatchCheck", false)
                            txtPasswordCheckWarning.visibility = View.VISIBLE
                        }else {
                            signupViewModel.setSignUpCheck("passwordCheck", true)
                            signupViewModel.setSignUpCheck("passwordMatchCheck", true)
                            txtPasswordCheckWarning.visibility = View.GONE
                        }
                    }else {
                        signupViewModel.setSignUpCheck("passwordCheck", false)
                        signupViewModel.updateInput("password", "")
                        txtPasswordWarning.visibility = View.VISIBLE
                    }
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
                        signupViewModel.setSignUpCheck("passwordCheck", false)
                        signupViewModel.setSignUpCheck("passwordMatchCheck", false)
                        txtPasswordCheckWarning.visibility = View.VISIBLE
                    }else {
                        signupViewModel.setSignUpCheck("passwordCheck", true)
                        signupViewModel.setSignUpCheck("passwordMatchCheck", true)
                        txtPasswordCheckWarning.visibility = View.GONE
                    }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            etNickname.textChange()
                .debounce(500)
                .map { it.toString() }
                .distinctUntilChanged()
                .collectLatest { input ->
                    if(input != ""){
                        signupViewModel.updateInput("nickname", input)
                        signupViewModel.nicknameCheck(input)
                    }else {
                        signupViewModel.updateInput("nickname", "")
                        signupViewModel.setSignUpCheck("nicknameCheck", false)
                    }
                }
        }

        btnSignUp.setOnClickListener {
            signupViewModel.signup()
        }
    }

    private fun observeViewModel() = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                signupViewModel.signupCheck.collectLatest { state ->
                    if(state.idCheck && state.emailCheck && state.passwordCheck && state.passwordMatchCheck && state.nicknameCheck){
                        btnSignUp.setBackgroundResource(R.drawable.btn_main_no_10)
                        btnSignUp.isEnabled = true
                    }else {
                        btnSignUp.setBackgroundResource(R.drawable.btn_e9e9e9_10)
                        btnSignUp.isEnabled = false
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                signupViewModel.idCheckResult.collect { state ->
                    when(state){
                        is Resource.Success -> {
                            signupViewModel.setSignUpCheck("idCheck", true)
                            txtIdWarning.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            signupViewModel.setSignUpCheck("idCheck", false)
                            txtIdWarning.visibility = View.VISIBLE
                            txtIdWarning.text = "❗이미 사용 중인 아이디 입니다."
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                signupViewModel.emailCheckResult.collect { state ->
                    when(state){
                        is Resource.Success -> {
                            signupViewModel.setSignUpCheck("emailCheck", true)
                            txtEmailWarning.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            signupViewModel.setSignUpCheck("emailCheck", false)
                            txtEmailWarning.visibility = View.VISIBLE
                            txtEmailWarning.text = "❗️이미 사용 중인 이메일 입니다."
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                signupViewModel.nicknameCheckResult.collect { state ->
                    when(state){
                        is Resource.Success -> {
                            signupViewModel.setSignUpCheck("nicknameCheck", true)
                            txtNicknameWarning.visibility = View.GONE
                        }
                        is Resource.Error -> {
                            signupViewModel.setSignUpCheck("nicknameCheck", false)
                            txtNicknameWarning.visibility = View.VISIBLE
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                signupViewModel.eventFlow.collect { event ->
                    when (event) {
                        is SignupViewModel.UiEvent.ShowToast -> Unit
                        is SignupViewModel.UiEvent.NavigateToSuccessScreen -> {
                            findNavController().navigate(R.id.action_inputFragment_to_successFragment)
                        }
                    }
                }
            }
        }
    }
}