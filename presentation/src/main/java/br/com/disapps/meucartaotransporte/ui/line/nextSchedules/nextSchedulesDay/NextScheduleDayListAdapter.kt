package br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay

import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.CustomViewHolder
import com.chad.library.adapter.base.BaseQuickAdapter

class NextScheduleDayListAdapter(data: List<LineSchedule>) : BaseQuickAdapter<LineSchedule, CustomViewHolder>(R.layout.item_next_schedules, data){

    override fun convert(helper: CustomViewHolder, item: LineSchedule) {
        helper.setText(R.id.name_point, item.busStopName)
        if(item.nextSchedules.isNotEmpty()){
            helper.setSchedule(R.id.next_schedule_1, item.nextSchedules[0])
        }
        if(item.nextSchedules.size > 1){
            helper.setSchedule(R.id.next_schedule_2, item.nextSchedules[1])
        }
        if(item.nextSchedules.size > 2){
            helper.setSchedule(R.id.next_schedule_3, item.nextSchedules[2])
        }
    }
}