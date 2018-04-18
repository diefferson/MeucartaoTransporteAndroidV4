package br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection

import br.com.disapps.domain.model.BusStop
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.getBusColor
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ItineraryDirectionListAdapter(data : List<BusStop>?,private val lineColor:String) : BaseQuickAdapter<BusStop,BaseViewHolder>(R.layout.item_itinerary, data){

    override fun convert(helper: BaseViewHolder?, item: BusStop) {
        helper?.setText(R.id.name, item.name)
        helper?.setText(R.id.type, item.type)
        helper?.setBackgroundColor(R.id.ic_it_item, getBusColor(mContext, lineColor))
    }
}