package br.com.disapps.meucartaotransporte.base.widgets.busSchedules

import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.meucartaotransporte.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class StopsListAdapter(data : List<LineSchedule>?) : BaseQuickAdapter<LineSchedule, BaseViewHolder>(R.layout.item_itinerary, data){

    override fun convert(helper: BaseViewHolder, item: LineSchedule) {
        helper.setText(R.id.name, item.busStopName)
    }

    fun getStop(position: Int): LineSchedule {
        return  data[position]
    }
}