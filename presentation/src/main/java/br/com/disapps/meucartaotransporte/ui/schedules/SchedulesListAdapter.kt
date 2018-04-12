package br.com.disapps.meucartaotransporte.ui.schedules

import br.com.disapps.domain.model.Schedule
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.validateSchedule
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class SchedulesListAdapter(data: List<Schedule>, var day : Int= 0) : BaseQuickAdapter<Schedule, BaseViewHolder>(R.layout.item_schedule, data){

    override fun convert(helper: BaseViewHolder, item: Schedule) {
        helper.setText(R.id.schedule, item.time)
        helper.setGone(R.id.adapt, item.adapt)

        if(validateSchedule(item, day)){
            helper.setAlpha(R.id.item_schedule, 1f)
        }else{
            helper.setAlpha(R.id.item_schedule,0.5f)
        }
    }
}