package br.com.disapps.meucartaotransporte.ui.lines

import android.app.Activity
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.custom.CustomViewHolder
import br.com.disapps.meucartaotransporte.util.getBusColor
import com.appodeal.ads.Appodeal
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView

class LinesListAdapter(data: List<ItemListLines>?, var activity: Activity) : BaseMultiItemQuickAdapter<LinesListAdapter.ItemListLines, CustomViewHolder>(data), FastScrollRecyclerView.SectionedAdapter {

    init {
        addItemType(ItemListLines.LINE_ITEM, R.layout.item_line)
        addItemType(ItemListLines.ADS_ITEM, R.layout.item_ads)
        Appodeal.cache(activity, Appodeal.NATIVE)
    }

    override fun convert(helper: CustomViewHolder, item: ItemListLines) {

        when(item.type){

            ItemListLines.ADS_ITEM ->{
                val ads = Appodeal.getNativeAds(1)
                if(ads.size>0){
                    helper.setNativeListAd(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ItemListLines.LINE_ITEM ->{
                item.line?.let {line->
                    helper.setText(R.id.line_code, line.code)
                    helper.setText(R.id.line_name, line.name)
                    helper.setText(R.id.line_type, line.category)
                    helper.addOnClickListener(R.id.fav_line)
                    helper.setBackgroundColor(R.id.ic_line, getBusColor(mContext, line.color))

                    if (line.favorite) {
                        helper.setImageResource(R.id.fav_line, R.drawable.star_grey)
                    } else {
                        helper.setImageResource(R.id.fav_line, R.drawable.star_outline_grey)
                    }
                }
            }
        }
    }

    override fun getSectionName(position: Int): String {
        return if(data[position].type == ItemListLines.LINE_ITEM){
            data[position].line!!.name[0].toString()
        }else{
            if(position == 0){
                "A"
            }else{
                data[position-1].line!!.name[0].toString()
            }
        }
    }

    class ItemListLines(lineItem: LineVO?, typeItem : Int) : MultiItemEntity{

        companion object {
            const val LINE_ITEM = 0
            const val ADS_ITEM = 1
        }

        var line :LineVO? = lineItem
        var type : Int
        init {
            type = if(typeItem == ADS_ITEM){
                ADS_ITEM
            }else{
                LINE_ITEM
            }
        }

        override fun getItemType(): Int {
            return type
        }
    }

    companion object {

        fun objectToItem(lineItem: LineVO?, typeItem: Int): ItemListLines {
            return ItemListLines(lineItem, typeItem)
        }

        fun objectToItem(lines :List<LineVO>) : List<ItemListLines>{

            val list = ArrayList<ItemListLines>()
            var i = 0

            lines.forEach {
                list.add(objectToItem(it,ItemListLines.LINE_ITEM))
                if(i%15 == 0){
                    list.add(objectToItem(null,ItemListLines.ADS_ITEM))
                }
                i++
            }
            return list
        }
    }
}

