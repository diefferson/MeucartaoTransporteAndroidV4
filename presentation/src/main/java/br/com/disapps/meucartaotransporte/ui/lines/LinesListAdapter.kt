package br.com.disapps.meucartaotransporte.ui.lines

import android.app.Activity
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.util.getBusColor
import br.com.disapps.meucartaotransporte.util.loadAdIfIsPro
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.gms.ads.AdView
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView

class LinesListAdapter(data: List<ListItem>?, var activity: Activity) :
        BaseMultiItemQuickAdapter<LinesListAdapter.ListItem,BaseViewHolder>(data), FastScrollRecyclerView.SectionedAdapter {

    init {
        addItemType(ListItem.LINE_ITEM, R.layout.item_line)
        addItemType(ListItem.ADS_BANNER, R.layout.ad_banner)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {

        when(item.type){

            ListItem.ADS_BANNER ->{
                (helper.itemView as AdView).loadAdIfIsPro()
            }

            ListItem.LINE_ITEM ->{
                helper.setText(R.id.line_code, item.line.code)
                helper.setText(R.id.line_name, item.line.name)
                helper.setText(R.id.line_type, item.line.category)
                helper.addOnClickListener(R.id.fav_line)
                helper.setBackgroundColor(R.id.ic_line, getBusColor(mContext, item.line.color))

                if (item.line.favorite) {
                    helper.setImageResource(R.id.fav_line, R.drawable.star_grey)
                } else {
                    helper.setImageResource(R.id.fav_line, R.drawable.star_outline_grey)
                }
            }
        }
    }

    override fun getSectionName(position: Int): String {
        return try {
            if(data[position].type == ListItem.LINE_ITEM){
                try {
                    data[position].line.name[0].toString()
                }catch (e:Exception){
                    ""
                }
            }else{
                if(position == 0){
                    "A"
                }else{
                    try {
                        data[position-1].line.name[0].toString()
                    }catch (e:Exception){
                        ""
                    }
                }
            }
        }catch (e:Exception){
            ""
        }
    }

    fun getLine(position: Int):ListItem{
        return  data[position]
    }

    class ListItem(lineItem: LineVO, typeItem : Int) : MultiItemEntity{

        var line : LineVO = lineItem
        var type : Int = typeItem

        override fun getItemType() = type

        companion object {
            const val LINE_ITEM = 0
            const val ADS_BANNER = 1
        }
    }

    companion object {

        fun objectToItem(lineItem: LineVO, typeItem: Int): ListItem {
            return ListItem(lineItem, typeItem)
        }

        fun objectToItem(lines :List<LineVO>?) : List<ListItem>{

            val list = ArrayList<ListItem>()
            var i = 0

            lines?.forEach {
                list.add(objectToItem(it,ListItem.LINE_ITEM))
                if(App.instance!= null && !App.instance!!.preferences.getIsProSync()) {
                    if (i % 15 == 0) {
                        list.add(objectToItem(getEmptyLine(), ListItem.ADS_BANNER))
                    }
                }
                i++
            }
            return list
        }

        private fun getEmptyLine() : LineVO{
            return LineVO("", "", "", "", false, "")
        }
    }
}

