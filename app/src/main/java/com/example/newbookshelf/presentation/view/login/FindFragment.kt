package com.example.newbookshelf.presentation.view.login

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.find.FindIdData
import com.example.newbookshelf.data.model.find.FindPwData
import com.example.newbookshelf.data.model.signup.EmailCheckData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.data.util.textChange
import com.example.newbookshelf.databinding.FragmentFindBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.viewmodel.find.FindViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FindFragment : Fragment() {
    private lateinit var binding: FragmentFindBinding
    private lateinit var findViewModel: FindViewModel

    companion object {
        const val TAG = "FindFragment"
    }

    private lateinit var title: String
    private var isEmail = false
    private var isNickname = false
    private var isId = false

    private var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_find, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFindBinding.bind(view)

        val args: FindFragmentArgs by navArgs()
        title = args.title

        init()
        bindViews()
        observeViewModels()
    }

    private fun init() = with(binding){
        findViewModel = (activity as LoginActivity).findViewModel

        cl.setOnClickListener {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(etChange.windowToken, 0)
        }

        if(title == "아이디"){
            tvToolbarTitle.text = "아이디 찾기"
            tvInfo.text = "가입 당시 등록한 이메일과 닉네임으로\n아이디를 조회할 수 있습니다.\n\n간편 로그인 계정이 아닌 일반 회원가입 계정만\n조회 가능합니다."
            tvChange.text = "닉네임"
            etChange.hint = "닉네임을 입력하세요."
            btnFind.text = "아이디 찾기"
        }else {
            tvToolbarTitle.text = "비밀번호 찾기"
            tvInfo.text = "가입 당시 정보를 입력하시면\n해당 계정의 이메일로 임시 비밀번호를 보내드립니다.\n\n간편 로그인 계정이 아닌 일반 회원가입 계정만\n조회가 가능합니다."
            tvChange.text = "아이디"
            etChange.hint = "아이디를 입력하세요."
            btnFind.text = "비밀번호 찾기"
        }
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            etEmail.textChange()
                .debounce(500)
                .map { it.toString() }
                .distinctUntilChanged()
                .collectLatest { input ->
                    isEmail = input != ""
                    checkNextButtonEnable()
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            etChange.textChange()
                .debounce(500)
                .map { it.toString() }
                .distinctUntilChanged()
                .collectLatest { input ->
                    if(title == "아이디"){
                        isNickname = input != ""
                    }else {
                        isId = input != ""
                    }
                    checkNextButtonEnable()
                }
        }

        btnFind.setOnClickListener {
            if(title == "아이디"){
                val findIdData = FindIdData(etEmail.text.toString().trim(), etChange.text.toString().trim())
                findViewModel.findId(findIdData)
            }else {
                val findPwData = FindPwData(etEmail.text.toString().trim(), etChange.text.toString().trim())
                findViewModel.findPw(findPwData)
            }
        }
    }

    private fun observeViewModels() = with(binding){
        viewLifecycleOwner.lifecycleScope.launch {
            findViewModel.findResult.collect { response ->
                when(response){
                    is Resource.Success -> {
                        if(title == "아이디"){
                            bundle = Bundle().apply {
                                putString("title", "아이디")
                            }
                            findViewModel.id.postValue(response.data!!.data.data)
                            findNavController().navigate(R.id.action_findFragment_to_findSuccessFragment, bundle!!)
                        }else {
                            bundle = Bundle().apply {
                                putString("title", "비밀번호")
                            }
                            findViewModel.email.postValue(etEmail.text.toString().trim())
                            if(isEmail && isId){
                                findNavController().navigate(R.id.action_findFragment_to_findSuccessFragment, bundle!!)
                            }
                        }
                        etEmail.setText("")
                        etChange.setText("")
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "입력하신 정보를 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun checkNextButtonEnable() = with(binding) {
        if(isEmail && isNickname || isEmail && isId){
            btnFind.isEnabled = true
            btnFind.setBackgroundResource(R.drawable.btn_main_no_10)
        }else {
            btnFind.isEnabled = false
            btnFind.setBackgroundResource(R.drawable.btn_e9e9e9_10)
        }
    }
}