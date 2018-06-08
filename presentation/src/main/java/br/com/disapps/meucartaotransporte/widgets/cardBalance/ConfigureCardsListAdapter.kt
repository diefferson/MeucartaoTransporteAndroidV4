package br.com.disapps.meucartaotransporte.widgets.cardBalance

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ConfigureCardsListAdapter(data :List<CardVO>) : BaseQuickAdapter<CardVO, BaseViewHolder>(R.layout.card_balance_widget_configure_item, data){

    override fun convert(helper: BaseViewHolder, item: CardVO) {
        helper.setText(R.id.card_name, item.name)
        helper.setText(R.id.card_code, mContext.getString(R.string.label_code, item.code))
        helper.setText(R.id.card_cpf, mContext.getString(R.string.label_cpf,item.cpf))
    }
}