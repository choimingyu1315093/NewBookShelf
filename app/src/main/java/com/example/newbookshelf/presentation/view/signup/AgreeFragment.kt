package com.example.newbookshelf.presentation.view.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentAgreeBinding
import com.example.newbookshelf.presentation.view.login.LoginActivity
import com.example.newbookshelf.presentation.view.signup.dialog.WebViewDialog
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AgreeFragment : Fragment() {
    private lateinit var binding: FragmentAgreeBinding
    lateinit var signupViewModel: SignupViewModel

    companion object {
        const val TAG = "AgreePageFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_agree, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAgreeBinding.bind(view)

        init()
        bindViews()
        observeViewModel()
    }

    private fun init(){
        signupViewModel = (activity as SignUpActivity).signupViewModel
    }

    private fun bindViews() = with(binding){
        cbAll.setOnCheckedChangeListener { _, isChecked ->
            signupViewModel.setAllChecked(isChecked)
        }

        cb1.setOnCheckedChangeListener { _, isChecked ->
            signupViewModel.setCheckBox(1, isChecked)
        }

        cb2.setOnCheckedChangeListener { _, isChecked ->
            signupViewModel.setCheckBox(2, isChecked)
        }

        cb3.setOnCheckedChangeListener { _, isChecked ->
            signupViewModel.setCheckBox(3, isChecked)
        }

        cb4.setOnCheckedChangeListener { _, isChecked ->
            signupViewModel.setCheckBox(4, isChecked)
        }

        ivCb1.setOnClickListener { openDialog("https://velog.io/@jongwoong0401/%EC%B1%85%EA%BD%82%EC%9D%B4-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80") }
        ivCb2.setOnClickListener { openDialog("https://velog.io/@jongwoong0401/%EC%B1%85%EA%BD%82%EC%9D%B4-%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4%EC%B2%98%EB%A6%AC%EB%B0%A9%EC%B9%A8") }
        ivCb3.setOnClickListener { openDialog("https://velog.io/@jongwoong0401/%EC%B1%85%EA%BD%82%EC%9D%B4-%EC%9C%84%EC%B9%98%EA%B8%B0%EB%B0%98%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9D%B4%EC%9A%A9%EC%95%BD%EA%B4%80") }
        ivCb4.setOnClickListener { openDialog("https://naver.com") }

        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_agreeFragment_to_inputFragment)
        }
    }

    private fun observeViewModel() = with(binding){
        lifecycleScope.launch {
            signupViewModel.agreeState.collectLatest { state ->
                cb1.isChecked = state.isCb1Checked
                cb2.isChecked = state.isCb2Checked
                cb3.isChecked = state.isCb3Checked
                cb4.isChecked = state.isCb4Checked
                cbAll.isChecked = state.isAllChecked

                btnNext.isEnabled = state.isNextEnabled
                val bgRes = if (state.isNextEnabled) R.drawable.btn_main_no_10 else R.drawable.btn_e9e9e9_10
                btnNext.background = ContextCompat.getDrawable(requireContext(), bgRes)
            }
        }
    }

    private fun openDialog(link: String) {
        val dialog = WebViewDialog(link)
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light)
        dialog.show(requireActivity().supportFragmentManager, "WebViewDialog")
    }
}