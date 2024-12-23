package com.example.newbookshelf.presentation.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newbookshelf.BookShelfApp
import com.example.newbookshelf.R
import com.example.newbookshelf.databinding.ItemFilterBinding
import com.gun0912.tedpermission.provider.TedPermissionProvider.context

class FilterForeignAdapter: RecyclerView.Adapter<FilterForeignAdapter.ViewHolder>() {

    var type = "week"

    private val foreignSearchList = arrayListOf<String>("소설/시", "에세이(외국)", "어린이(외국)", "경제경영(외국)", "인문/사회", "컴퓨터", "자기계발(외국)", "여행(외국)", "건강/스포츠")
    private val foreignSearchMap: Map<String, Int> = mapOf(
        "소설/시" to 90842, "에세이(외국)" to 90845, "어린이(외국)" to 106165, "경제경영(외국)" to 90835, "인문/사회" to 90853, "컴퓨터" to 90859, "자기계발(외국)" to 90854
        , "여행(외국)" to 90846, "건강/스포츠" to 90833)

    private var onItemClickListener: ((String, Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (String, Int) -> Unit){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foreignSearchList[position])
    }

    override fun getItemCount(): Int {
        return foreignSearchList.size
    }

    inner class ViewHolder(private val binding: ItemFilterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(foreignSearch: String) = with(binding){
            btnSearch.text = foreignSearch

            var filter = ""
            if(type == "week"){
                filter = BookShelfApp.prefs.getFilter("filter", "")
            }else if(type == "new"){
                filter = BookShelfApp.prefs.getNewFilter("newFilter", "")
            }else {
                filter = BookShelfApp.prefs.getAttentionFilter("attentionFilter", "")
            }

            if (foreignSearch == filter) {
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
                    it("Foreign", foreignSearchMap[btnSearch.text.toString()]!!)
                }
            }
        }
    }
}