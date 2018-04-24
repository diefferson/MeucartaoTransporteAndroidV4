package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.graphics.Color
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class BalanceListAdapter(data : List<CardVO>) : BaseQuickAdapter<CardVO, BaseViewHolder>(R.layout.item_balance, data){

    override fun convert(helper: BaseViewHolder, item: CardVO) {
        helper.setText(R.id.card_status, mContext.getString(R.string.card_state, item.status))
        helper.setText(R.id.card_type, mContext.getString(R.string.card_type, item.type))
        helper.setText(R.id.card_balance, mContext.getString(R.string.card_balance_value, String.format("%.2f",item.balance)))
        helper.setText(R.id.card_balance_date, mContext.getString(R.string.card_updated, item.balanceDate))

        if(item.balance >15){
            helper.setTextColor(R.id.card_balance, Color.GREEN)
        }else{
            helper.setTextColor(R.id.card_balance, Color.RED)
        }
    }
}