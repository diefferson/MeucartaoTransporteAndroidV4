package br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay

import android.app.Activity
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.ui.custom.setSchedule
import br.com.disapps.meucartaotransporte.util.loadAdIfIsPro
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.gms.ads.AdView

class NextScheduleDayListAdapter(data: List<ListItem>, var activity: Activity) : BaseMultiItemQuickAdapter<NextScheduleDayListAdapter.ListItem, BaseViewHolder>(data){

    init {
        addItemType(ListItem.LINE_SCHEDULE_ITEM, R.layout.item_next_schedules)
        addItemType(ListItem.ADS_BANNER, R.layout.ad_banner)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {
        when(item.type){

            ListItem.ADS_BANNER ->{
                (helper.itemView as AdView).loadAdIfIsPro()
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
            const val ADS_BANNER = 1
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
                if(App.instance!= null && !App.instance!!.preferences.getIsProSync()) {
                    if (i % 2 == 0) {
                        list.add(objectToItem(getEmptyLineSchedule(), ListItem.ADS_BANNER))
                    }
                }
                i++
            }

            return list
        }

        private fun getEmptyLineSchedule() : LineSchedule{
            return LineSchedule("", 0, "", "", ArrayList())
        }
    }
}