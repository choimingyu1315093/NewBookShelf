package com.example.newbookshelf.presentation.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentChargeBinding
import com.example.newbookshelf.presentation.view.login.FindFragmentArgs

class ChargeFragment : Fragment() {
    private lateinit var binding: FragmentChargeBinding

    companion object {
        const val TAG = "ChargeFragment"
    }

    private var ticket = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_charge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChargeBinding.bind(view)

        val args: ChargeFragmentArgs by navArgs()
        ticket = args.ticket

        init()
        bindViews()
    }

    private fun init() = with(binding){
        tvTicket.text = "낙엽 ${ticket}장"
    }

    private fun bindViews() = with(binding){
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}