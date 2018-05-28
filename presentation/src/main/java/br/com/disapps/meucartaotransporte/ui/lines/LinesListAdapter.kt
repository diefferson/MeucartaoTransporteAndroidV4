package br.com.disapps.meucartaotransporte.ui.lines

import android.app.Activity
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdAppWall
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdContentStream
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdFedd
import br.com.disapps.meucartaotransporte.util.getBusColor
import com.appodeal.ads.Appodeal
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView

class LinesListAdapter(data: List<ListItem>?, var activity: Activity) :
        BaseMultiItemQuickAdapter<LinesListAdapter.ListItem,BaseViewHolder>(data), FastScrollRecyclerView.SectionedAdapter {

    init {
        addItemType(ListItem.LINE_ITEM, R.layout.item_line)
        addItemType(ListItem.ADS_FEED_ITEM, R.layout.item_ads_feed)
        addItemType(ListItem.ADS_CONTENT_STREAM_ITEM, R.layout.item_ads_content_stream)
        addItemType(ListItem.ADS_APP_WALL_ITEM, R.layout.item_ads_app_wall)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {

        when(item.type){

            ListItem.ADS_FEED_ITEM ->{
                val ads = Appodeal.getNativeAds(1)
                if(ads.size>0){
                    helper.setNativeAdFedd(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ListItem.ADS_CONTENT_STREAM_ITEM ->{
                val ads = Appodeal.getNativeAds(1)
                if(ads.size>0){
                    helper.setNativeAdContentStream(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ListItem.ADS_APP_WALL_ITEM ->{
                val ads = Appodeal.getNativeAds(1)
                if(ads.size>0){
                    helper.setNativeAdAppWall(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
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
        return if(data[position].type == ListItem.LINE_ITEM){
            data[position].line.name[0].toString()
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
            const val ADS_FEED_ITEM = 1
            const val ADS_CONTENT_STREAM_ITEM = 2
            const val ADS_APP_WALL_ITEM = 3
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
                if(i%15 == 0){
                    list.add(objectToItem(getEmptyLine(),ListItem.ADS_FEED_ITEM))
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

