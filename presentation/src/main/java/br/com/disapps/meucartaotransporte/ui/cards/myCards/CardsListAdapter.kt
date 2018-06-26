package br.com.disapps.meucartaotransporte.ui.cards.myCards

import android.app.Activity
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.util.loadAdIfIsPro
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.gms.ads.AdView


class CardsListAdapter(data: List<ListItem>?, var activity: Activity) : BaseMultiItemQuickAdapter<CardsListAdapter.ListItem, BaseViewHolder>(data){

    init {
        addItemType(ListItem.CARD_ITEM, R.layout.item_card)
        addItemType(ListItem.ADS_BANNER, R.layout.ad_banner)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {

        when(item.type){

            ListItem.CARD_ITEM ->{
                helper.setText(R.id.card_name, item.card.name)
                helper.setText(R.id.card_code, item.card.code)
                helper.setText(R.id.card_cpf, item.card.cpf)
                helper.setText(R.id.card_status, item.card.status)
                helper.setText(R.id.card_type, item.card.type)
                helper.setText(R.id.card_balance, mContext.getString(R.string.card_balance_value, String.format("%.2f", item.card.balance)))
                helper.setText(R.id.card_date_balance, item.card.balanceDate)
                helper.addOnClickListener(R.id.btn_card_balance)
                helper.addOnClickListener(R.id.btn_card_extract)
                helper.addOnClickListener(R.id.ic_delete_card)
            }

            ListItem.ADS_BANNER ->{
                (helper.itemView as AdView).loadAdIfIsPro()
            }
        }
    }

    fun getCard(position:Int): CardVO{
        return data[position].card
    }

    class ListItem(cardItem: CardVO, typeItem : Int) : MultiItemEntity {

        var card : CardVO = cardItem
        var type : Int = typeItem

        override fun getItemType() = type

        companion object {
            const val CARD_ITEM = 0
            const val ADS_BANNER = 1
        }
    }

    companion object {

        fun objectToItem(cardItem: CardVO, typeItem: Int): ListItem {
            return ListItem(cardItem, typeItem)
        }

        fun objectToItem(extract :List<CardVO>?) : List<ListItem>{

            val list = ArrayList<ListItem>()

            extract?.forEach {
                list.add(objectToItem(it,ListItem.CARD_ITEM))
                if(App.instance!= null && !App.instance!!.preferences.getIsProSync()) {
                    list.add(objectToItem(getEmptyCard(), ListItem.ADS_BANNER))
                }
            }

            return list
        }

        private fun getEmptyCard() : CardVO{
            return CardVO("", "")
        }
    }
}