package com.example.newbookshelf.presentation.view.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.data.model.home.searchbook.SearchBookResult
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.example.newbookshelf.databinding.ItemFilterBinding

class FilterKoreaAdapter: RecyclerView.Adapter<FilterKoreaAdapter.ViewHolder>() {

    var type = "week"

    private val koreaSearchList = arrayListOf<String>("전체 국내 도서", "자기계발", "여행", "소설/시/희곡", "경제경영", "건강/취미/레저", "가정/요리/뷰티", "과학", "만화", "어린이", "에세이"
        , "역사", "예술/대중문화", "외국어", "유아", "인문학", "장르소설", "종교/역학", "컴퓨터/모바일")
    private val koreaSearchMap: Map<String, Int> = mapOf(
        "전체 국내 도서" to 0, "자기계발" to 336, "여행" to 1196, "소설/시/희곡" to 1, "경제경영" to 170, "건강/취미/레저" to 55890, "가정/요리/뷰티" to 1230, "과학" to 987
        , "만화" to 2551, "어린이" to 1108, "에세이" to 55889, "역사" to 74, "예술/대중문화" to 517, "외국어" to 1322, "유아" to 13789, "인문학" to 656, "장르소설" to 112011, "종교/역학" to 1237, "컴퓨터/모바일" to 351)

    private var onItemClickListener: ((String, Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (String, Int) -> Unit){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(koreaSearchList[position])
    }

    override fun getItemCount(): Int {
        return koreaSearchList.size
    }

    inner class ViewHolder(private val binding: ItemFilterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(koreaSearch: String) = with(binding){
            btnSearch.text = koreaSearch

            var filter = ""
            if(type == "week"){
                filter = BookShelfApp.prefs.getFilter("filter", "")
            }else if(type == "new"){
                filter = BookShelfApp.prefs.getNewFilter("newFilter", "")
            }else {
                filter = BookShelfApp.prefs.getAttentionFilter("attentionFilter", "")
            }

            if (koreaSearch == filter) {
                btnSearch.isSelected = true
                btnSearch.setBackgroundResource(R.drawable.bg_main_no_30)
                btnSearch.setTextColor(ContextCompat.getColor(context, R.color.white))

            } else {
                btnSearch.isSelected = false
                btnSearch.setBackgroundResource(R.drawable.bg_no_767676_30)
                btnSearch.setTextColor(ContextCompat.getColor(context, R.color.color767676))
            }

            btnSearch.setOnClickListener {
                if(type == "week"){
                    BookShelfApp.prefs.setFilter("filter", btnSearch.text.toString())
                }else if(type == "new"){
                    BookShelfApp.prefs.setNewFilter("newFilter", btnSearch.text.toString())
                }else {
                    BookShelfApp.prefs.setAttentionFilter("attentionFilter", btnSearch.text.toString())
                }

                onItemClickListener?.let {
                    it("Book", koreaSearchMap[btnSearch.text.toString()]!!)
                }
            }
        }
    }
}