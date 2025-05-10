package com.example.newbookshelf.presentation.view.signup

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.setting.TicketData
import com.example.newbookshelf.data.util.Resource
import com.example.newbookshelf.databinding.FragmentSuccessBinding
import com.example.newbookshelf.presentation.view.login.LoginActivity
import com.example.newbookshelf.presentation.viewmodel.signup.SignupViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : DialogFragment() {
    private lateinit var binding: FragmentSuccessBinding
    private lateinit var signupViewModel: SignupViewModel
    
    companion object {
        const val TAG = "SuccessFragment"
    }

    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSuccessBinding.bind(view)

        init()
    }

    private fun init() = with(binding){
        signupViewModel = (activity as SignUpActivity).signupViewModel
        (activity as SignUpActivity).binding.tvTitle.text = "회원 가입 완료"
        (activity as SignUpActivity).binding.ivBack.visibility = View.GONE
        accessToken = BookShelfApp.prefs.getAccessToken("accessToken", "")
        lifecycleScope.launch {
            finish()
        }
    }

    private suspend fun finish(){
        Toast.makeText(requireContext(), "가입해주셔서 감사합니다.\n이벤트로 교환권을 지급해 드립니다.", Toast.LENGTH_SHORT).show()
        delay(4300)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }
}