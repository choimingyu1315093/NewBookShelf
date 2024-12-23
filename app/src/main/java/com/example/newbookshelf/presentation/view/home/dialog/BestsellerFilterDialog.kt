package com.example.newbookshelf.presentation.view.home.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.FragmentBestsellerFilterDialogBinding
import com.example.newbookshelf.presentation.view.home.HomeActivity
import com.example.newbookshelf.presentation.view.home.adapter.FilterForeignAdapter
import com.example.newbookshelf.presentation.view.home.adapter.FilterKoreaAdapter


class BestsellerFilterDialog(private val onApplyListener: OnApplyListener, private val type: String) : DialogFragment() {
    private lateinit var binding: FragmentBestsellerFilterDialogBinding

    interface OnApplyListener {
        fun next(b: Boolean, type: String)
    }

    companion object {
        const val TAG = "BestsellerFilterDialog"
    }

    private lateinit var filterKoreaAdapter: FilterKoreaAdapter
    private lateinit var filterForeignAdapter: FilterForeignAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStart() {
        super.onStart()
        dialog?.window?.decorView?.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val dialogBounds = Rect()
                dialog?.window?.decorView?.getHitRect(dialogBounds)

                if (!dialogBounds.contains(event.x.toInt(), event.y.toInt())) {
                    onApplyListener.next(true, type)
                    dismiss()
                }
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels
        dialog?.window?.setLayout((width * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bestseller_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBestsellerFilterDialogBinding.bind(view)

        init()
        bindViews()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init() = with(binding){
        filterKoreaAdapter = (activity as HomeActivity).filterKoreaAdapter
        filterKoreaAdapter.type = type
        filterKoreaAdapter.setOnItemClickListener { country, num ->
            if(type == "week"){
                BookShelfApp.prefs.setSearchTarget("searchTarget", country)
                BookShelfApp.prefs.setCategoryId("categoryId", num)
            }else if(type == "new"){
                BookShelfApp.prefs.setNewSearchTarget("newSearchTarget", country)
                BookShelfApp.prefs.setNewCategoryId("newCategoryId", num)
            }else {
                BookShelfApp.prefs.setAttentionSearchTarget("attentionSearchTarget", country)
                BookShelfApp.prefs.setAttentionCategoryId("attentionCategoryId", num)
            }

            filterKoreaAdapter.notifyDataSetChanged()
            filterForeignAdapter.notifyDataSetChanged()
        }
        rvKoreaFilter.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = filterKoreaAdapter
        }

        filterForeignAdapter = (activity as HomeActivity).filterForeignAdapter
        filterForeignAdapter.type = type
        filterForeignAdapter.setOnItemClickListener { country, num ->
            if(type == "week"){
                BookShelfApp.prefs.setSearchTarget("searchTarget", country)
                BookShelfApp.prefs.setCategoryId("categoryId", num)
            }else if(type == "new"){
                BookShelfApp.prefs.setNewSearchTarget("newSearchTarget", country)
                BookShelfApp.prefs.setNewCategoryId("newCategoryId", num)
            }else {
                BookShelfApp.prefs.setAttentionSearchTarget("attentionSearchTarget", country)
                BookShelfApp.prefs.setAttentionCategoryId("attentionCategoryId", num)
            }
            filterKoreaAdapter.notifyDataSetChanged()
            filterForeignAdapter.notifyDataSetChanged()
        }
        rvForeignFilter.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = filterForeignAdapter
        }
    }

    private fun bindViews() = with(binding){
        btnOk.setOnClickListener {
            onApplyListener.next(true, type)
            dismiss()
        }
    }
}