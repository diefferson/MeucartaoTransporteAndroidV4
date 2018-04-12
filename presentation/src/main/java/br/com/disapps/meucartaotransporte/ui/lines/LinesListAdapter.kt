package br.com.disapps.meucartaotransporte.ui.lines

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.util.getBusColor
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView

class LinesListAdapter(data: List<LineVO>?) : BaseQuickAdapter<LineVO, BaseViewHolder>(R.layout.item_line, data), FastScrollRecyclerView.SectionedAdapter {

    override fun convert(helper: BaseViewHolder?, item: LineVO) {
        helper?.setText(R.id.line_code, item.code)
        helper?.setText(R.id.line_name, item.name)
        helper?.setText(R.id.line_type, item.category)
        helper?.addOnClickListener(R.id.fav_line)
        helper?.setBackgroundColor(R.id.ic_line, getBusColor(mContext, item.color))

        if (item.favorite) {
            helper?.setImageResource(R.id.fav_line, R.drawable.star_grey)
        } else {
            helper?.setImageResource(R.id.fav_line, R.drawable.star_outline_grey)
        }
    }

    override fun getSectionName(position: Int): String {
        return data[position].name[0].toString()
    }

}

