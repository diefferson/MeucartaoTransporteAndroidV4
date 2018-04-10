package br.com.disapps.meucartaotransporte.ui.lines

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView

class LinesListAdapter(data: List<LineVO>?) : BaseQuickAdapter<LineVO, BaseViewHolder>(R.layout.item_line, data), FastScrollRecyclerView.SectionedAdapter {

    override fun convert(helper: BaseViewHolder?, item: LineVO) {
        helper?.setText(R.id.ic_line, item.code)
        helper?.setText(R.id.line_name, item.name)
        helper?.setText(R.id.line_type, item.category)
        helper?.addOnClickListener(R.id.fav_line)

        when (item.color) {
            "blue" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_blue)
            "lightBlue" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_blue_light)
            "red" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_red)
            "grey" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_grey)
            "green" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_green)
            "yellow" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_yellow)
            "orange" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_orange)
            "white" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_white)
            "ccd" -> helper?.setBackgroundRes(R.id.ic_line, R.drawable.circle_ccd)
        }

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