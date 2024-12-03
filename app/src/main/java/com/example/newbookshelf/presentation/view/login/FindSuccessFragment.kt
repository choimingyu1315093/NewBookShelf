package com.example.newbookshelf.presentation.view.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentFindSuccessBinding
import com.example.newbookshelf.presentation.viewmodel.find.FindViewModel

class FindSuccessFragment : Fragment() {
    private lateinit var binding: FragmentFindSuccessBinding
    private lateinit var findViewModel: FindViewModel

    companion object {
        const val TAG = "FindSuccessFragment"
    }

    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_find_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFindSuccessBinding.bind(view)

        val args: FindFragmentArgs by navArgs()
        title = args.title

        init()
        bindViews()
    }

    private fun init() = with(binding){
        findViewModel = (activity as LoginActivity).findViewModel

        if(title == "아이디"){
            tvInfo.text = "회원님의 아이디는\n${findViewModel.id.value}입니다."
        }else {
            tvInfo.text = "${findViewModel.email.value}으로\n임시 비밀번호를 전송하였습니다."
            tvSubInfo.visibility = View.VISIBLE
            btnFindPw.visibility = View.GONE
        }
    }

    private fun bindViews() = with(binding){
        btnLogin.setOnClickListener {
            val intent = requireActivity().packageManager.getLaunchIntentForPackage("com.example.newbookshelf")
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent!!)
            Runtime.getRuntime().exit(0)
        }

        btnFindPw.setOnClickListener {
            val bundle = Bundle().apply {
                putString("title", "비밀번호")
            }
            findNavController().navigate(R.id.action_findSuccessFragment_to_findFragment, bundle)
        }
    }
}