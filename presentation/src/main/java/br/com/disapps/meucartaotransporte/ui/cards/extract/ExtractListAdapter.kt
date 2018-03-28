package br.com.disapps.meucartaotransporte.ui.cards.extract

import br.com.disapps.domain.model.Extract
import br.com.disapps.meucartaotransporte.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ExtractListAdapter(data: List<Extract>) : BaseQuickAdapter<Extract, BaseViewHolder>(R.layout.item_extract, data){

    override fun convert(helper: BaseViewHolder, item: Extract) {
        helper.setText(R.id.extract_balance,  mContext.getString(R.string.card_balance_value,String.format("%.2f",item.balance)))
        helper.setText(R.id.extract_date, item.date)
        helper.setText(R.id.extract_place, item.local)
        helper.setText(R.id.extract_value,  mContext.getString(R.string.card_balance_value,String.format("%.2f",item.value)))
    }
}