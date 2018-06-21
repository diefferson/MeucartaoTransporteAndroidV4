package br.com.disapps.meucartaotransporte.widgets.busSchedules

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.model.LineVO
import br.com.disapps.meucartaotransporte.base.util.getBusColor
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class LinesListAdapter(data: List<LineVO>?) : BaseQuickAdapter<LineVO,BaseViewHolder>(R.layout.item_line, data){
    
    override fun convert(helper: BaseViewHolder, item: LineVO) {
        helper.setText(R.id.line_code, item.code)
        helper.setText(R.id.line_name, item.name)
        helper.setText(R.id.line_type, item.category)
        helper.setBackgroundColor(R.id.ic_line, getBusColor(mContext, item.color))
        helper.setGone(R.id.fav_line, false)
    }
    
    fun getLine(position: Int): LineVO {
        return  data[position]
    }
}

