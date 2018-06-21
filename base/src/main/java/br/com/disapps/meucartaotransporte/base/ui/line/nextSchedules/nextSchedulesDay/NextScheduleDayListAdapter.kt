package br.com.disapps.meucartaotransporte.base.ui.line.nextSchedules.nextSchedulesDay

import android.app.Activity
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.ui.custom.setNativeAdAppWall
import br.com.disapps.meucartaotransporte.base.ui.custom.setNativeAdContentStream
import br.com.disapps.meucartaotransporte.base.ui.custom.setNativeAdFedd
import br.com.disapps.meucartaotransporte.base.ui.custom.setSchedule
import com.appodeal.ads.Appodeal
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

class NextScheduleDayListAdapter(data: List<ListItem>, var activity: Activity) : BaseMultiItemQuickAdapter<NextScheduleDayListAdapter.ListItem, BaseViewHolder>(data){

    init {
        addItemType(ListItem.LINE_SCHEDULE_ITEM, R.layout.item_next_schedules)
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

            ListItem.LINE_SCHEDULE_ITEM ->{
                var hasSchedules = false
                helper.setText(R.id.name_point, item.lineSchedule.busStopName)

                if(item.lineSchedule.nextSchedules.isNotEmpty()){
                    hasSchedules = true
                    helper.setGone(R.id.next_schedule_1, true)
                    helper.setSchedule(R.id.next_schedule_1, item.lineSchedule.nextSchedules[0])
                }else{
                    helper.setGone(R.id.next_schedule_1, false)
                }

                if(item.lineSchedule.nextSchedules.size > 1){
                    helper.setGone(R.id.next_schedule_2, true)
                    helper.setSchedule(R.id.next_schedule_2, item.lineSchedule.nextSchedules[1])
                }else{
                    helper.setGone(R.id.next_schedule_2, false)
                }

                if(item.lineSchedule.nextSchedules.size > 2){
                    helper.setGone(R.id.next_schedule_3, true)
                    helper.setSchedule(R.id.next_schedule_3, item.lineSchedule.nextSchedules[2])
                }else{
                    helper.setGone(R.id.next_schedule_3, false)
                }

                helper.setVisible(R.id.empty_schedules, !hasSchedules)
            }
        }

    }

    fun getLineSchedule(position:Int) : LineSchedule{
        return data[position].lineSchedule
    }

    class ListItem(lineScheduleItem: LineSchedule,  typeItem : Int) : MultiItemEntity {

        var lineSchedule : LineSchedule = lineScheduleItem
        var type : Int = typeItem

        override fun getItemType() = type

        companion object {
            const val LINE_SCHEDULE_ITEM = 0
            const val ADS_FEED_ITEM = 1
            const val ADS_CONTENT_STREAM_ITEM = 2
            const val ADS_APP_WALL_ITEM = 3
        }
    }

    companion object {

        fun objectToItem(lineScheduleItem: LineSchedule, typeItem: Int): ListItem {
            return ListItem(lineScheduleItem, typeItem)
        }

        fun objectToItem(items :List<LineSchedule>?) : List<ListItem>{

            val list = ArrayList<ListItem>()
            var i = 0

            items?.forEach {
                list.add(objectToItem(it, ListItem.LINE_SCHEDULE_ITEM))
                if (i % 2 == 0) {
                    list.add(objectToItem(getEmptyLineSchedule(), ListItem.ADS_APP_WALL_ITEM))
                }
                i++
            }

            if(list.size >0){
                list.add(objectToItem(getEmptyLineSchedule(), ListItem.ADS_CONTENT_STREAM_ITEM))
            }

            return list
        }

        private fun getEmptyLineSchedule() : LineSchedule{
            return LineSchedule("", 0, "", "", ArrayList())
        }
    }
}