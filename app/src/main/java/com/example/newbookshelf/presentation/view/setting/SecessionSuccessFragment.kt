package com.example.newbookshelf.presentation.view.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentSecessionSuccessBinding

class SecessionSuccessFragment : Fragment() {
    private lateinit var binding: FragmentSecessionSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_secession_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecessionSuccessBinding.bind(view)

        bindViews()
    }

    private fun bindViews() = with(binding){
        btnGo.setOnClickListener {
            val intent = requireActivity().packageManager.getLaunchIntentForPackage("com.example.newbookshelf")
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent!!)
            Runtime.getRuntime().exit(0)
        }
    }
}