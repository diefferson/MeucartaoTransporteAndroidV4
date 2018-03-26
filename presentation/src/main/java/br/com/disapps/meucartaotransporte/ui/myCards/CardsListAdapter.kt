package br.com.disapps.meucartaotransporte.ui.myCards

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class CardsListAdapter(data: List<CardVO>?) : BaseQuickAdapter<CardVO, BaseViewHolder>(R.layout.item_card, data){

    override fun convert(helper: BaseViewHolder, item: CardVO) {
        helper.setText(R.id.card_name, item.name)
        helper.setText(R.id.card_code, item.code)
        helper.setText(R.id.card_cpf, item.cpf)
        helper.setText(R.id.card_status, item.status)
        helper.setText(R.id.card_type, item.type)
        helper.setText(R.id.card_balance, mContext.getString(R.string.card_balance_value, String.format("%.2f", item.balance)))
        helper.setText(R.id.card_date_balance, item.balanceDate)
        helper.addOnClickListener(R.id.btn_card_balance)
        helper.addOnClickListener(R.id.btn_card_extract)
        helper.addOnClickListener(R.id.ic_delete_card)
    }
}