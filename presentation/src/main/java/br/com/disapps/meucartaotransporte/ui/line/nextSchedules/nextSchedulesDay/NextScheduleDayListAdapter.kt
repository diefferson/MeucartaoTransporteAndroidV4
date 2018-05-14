package br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay

import android.app.Activity
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.CustomViewHolder
import com.appodeal.ads.Appodeal
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity

class NextScheduleDayListAdapter(data: List<ItemListLineSchedule>,var activity: Activity) : BaseMultiItemQuickAdapter<NextScheduleDayListAdapter.ItemListLineSchedule, CustomViewHolder>(data){

    init {
        addItemType(ItemListLineSchedule.LINE_SCHEDULE_ITEM, R.layout.item_next_schedules)
        addItemType(ItemListLineSchedule.ADS_ITEM, R.layout.item_ads)
        Appodeal.cache(activity, Appodeal.NATIVE)
    }

    override fun convert(helper: CustomViewHolder, item: ItemListLineSchedule) {
        when(item.type){

            ItemListLineSchedule.ADS_ITEM ->{
                val ads = Appodeal.getNativeAds(1)
                if(ads.size>0){
                    helper.setNativeListAd(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ItemListLineSchedule.LINE_SCHEDULE_ITEM ->{
                helper.setText(R.id.name_point, item.lineSchedule?.busStopName)
                var hasSchedules = false

                item.lineSchedule?.nextSchedules?.let {
                    if(it.isNotEmpty()){
                        hasSchedules = true
                        helper.setGone(R.id.next_schedule_1, true)
                        helper.setSchedule(R.id.next_schedule_1, it[0])
                    }else{
                        helper.setGone(R.id.next_schedule_1, false)
                    }

                    if(it.size > 1){
                        helper.setGone(R.id.next_schedule_2, true)
                        helper.setSchedule(R.id.next_schedule_2, it[1])
                    }else{
                        helper.setGone(R.id.next_schedule_2, false)
                    }

                    if(it.size > 2){
                        helper.setGone(R.id.next_schedule_3, true)
                        helper.setSchedule(R.id.next_schedule_3, it[2])
                    }else{
                        helper.setGone(R.id.next_schedule_3, false)
                    }
                }

                helper.setVisible(R.id.empty_schedules, !hasSchedules)
            }
        }

    }

    class ItemListLineSchedule(lineScheduleItem: LineSchedule?, typeItem : Int) : MultiItemEntity {

        companion object {
            const val LINE_SCHEDULE_ITEM = 0
            const val ADS_ITEM = 1
        }

        var lineSchedule : LineSchedule? = lineScheduleItem
        var type : Int
        init {
            type = if(typeItem == ADS_ITEM){
                ADS_ITEM
            }else{
                LINE_SCHEDULE_ITEM
            }
        }

        override fun getItemType(): Int {
            return type
        }
    }

    companion object {

        fun objectToItem(lineScheduleItem: LineSchedule?, typeItem: Int): ItemListLineSchedule {
            return ItemListLineSchedule(lineScheduleItem, typeItem)
        }

        fun objectToItem(items :List<LineSchedule>) : List<ItemListLineSchedule>{

            val list = ArrayList<ItemListLineSchedule>()
            var i = 0
            items.forEach {
                list.add(objectToItem(it, ItemListLineSchedule.LINE_SCHEDULE_ITEM))
                if (i % 2 == 0) {
                    list.add(objectToItem(null, ItemListLineSchedule.ADS_ITEM))
                }
                i++
            }
            return list
        }
    }
}