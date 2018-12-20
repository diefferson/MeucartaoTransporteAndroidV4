package br.com.disapps.meucartaotransporte.ui.cards.balance

import android.app.Activity
import android.graphics.Color
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.util.loadAdIfIsPro
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.gms.ads.AdView

class BalanceListAdapter(data : List<ListItem>,var activity: Activity) : BaseMultiItemQuickAdapter<BalanceListAdapter.ListItem, BaseViewHolder>(data){

    init {
        addItemType(ListItem.BALANCE_ITEM, R.layout.item_balance)
        addItemType(ListItem.ADS_BANNER, R.layout.ad_banner)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {

        when(item.type){

            ListItem.BALANCE_ITEM ->{
                helper.setText(R.id.card_status, mContext.getString(R.string.card_state, item.balance.status))
                helper.setText(R.id.card_type, mContext.getString(R.string.card_type, item.balance.type))
                helper.setText(R.id.card_balance, mContext.getString(R.string.card_balance_value, String.format("%.2f",item.balance.balance)))
                helper.setText(R.id.card_balance_date, mContext.getString(R.string.card_updated, item.balance.balanceDate))

                if(item.balance.balance >15){
                    helper.setTextColor(R.id.card_balance, Color.GREEN)
                }else{
                    helper.setTextColor(R.id.card_balance, Color.RED)
                }
            }

            ListItem.ADS_BANNER ->{
                (helper.itemView as AdView).loadAdIfIsPro()
            }
        }
    }

    class ListItem(balanceItem: CardVO, typeItem : Int) : MultiItemEntity {

        var balance : CardVO = balanceItem
        var type : Int = typeItem

        override fun getItemType() = type

        companion object {
            const val BALANCE_ITEM = 0
            const val ADS_BANNER = 1
        }
    }

    companion object {

        fun objectToItem(balanceItem: CardVO, typeItem: Int): ListItem {
            return ListItem(balanceItem, typeItem)
        }

        fun objectToItem(balance :List<CardVO>?) : List<ListItem>{

            val list = ArrayList<ListItem>()

            balance?.forEach {
                list.add(objectToItem(it,ListItem.BALANCE_ITEM))
            }

            if(App.instance!= null && !App.instance!!.preferences.getIsProSync()) {

                if (list.size > 0) {
                    list.add(objectToItem(getEmptyCard(), ListItem.ADS_BANNER))
                }
            }

            return list
        }

        private fun getEmptyCard() : CardVO {
            return CardVO( "", "")
        }
    }
}