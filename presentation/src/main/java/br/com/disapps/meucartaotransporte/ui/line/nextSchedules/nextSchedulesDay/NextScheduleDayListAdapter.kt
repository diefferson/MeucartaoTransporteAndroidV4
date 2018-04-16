package br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay

import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.CustomViewHolder
import com.chad.library.adapter.base.BaseQuickAdapter

class NextScheduleDayListAdapter(data: List<LineSchedule>) : BaseQuickAdapter<LineSchedule, CustomViewHolder>(R.layout.item_next_schedules, data){

    override fun convert(helper: CustomViewHolder, item: LineSchedule) {
        helper.setText(R.id.name_point, item.busStopName)
        var hasSchedules = false
        if(item.nextSchedules.isNotEmpty()){
            hasSchedules = true
            helper.setGone(R.id.next_schedule_1, true)
            helper.setSchedule(R.id.next_schedule_1, item.nextSchedules[0])
        }else{
            helper.setGone(R.id.next_schedule_1, false)
        }

        if(item.nextSchedules.size > 1){
            helper.setGone(R.id.next_schedule_2, true)
            helper.setSchedule(R.id.next_schedule_2, item.nextSchedules[1])
        }else{
            helper.setGone(R.id.next_schedule_2, false)
        }

        if(item.nextSchedules.size > 2){
            helper.setGone(R.id.next_schedule_3, true)
            helper.setSchedule(R.id.next_schedule_3, item.nextSchedules[2])
        }else{
            helper.setGone(R.id.next_schedule_3, false)
        }

        helper.setVisible(R.id.empty_schedules, !hasSchedules)

    }
}