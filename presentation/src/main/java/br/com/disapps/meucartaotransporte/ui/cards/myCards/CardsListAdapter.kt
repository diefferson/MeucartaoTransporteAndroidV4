package br.com.disapps.meucartaotransporte.ui.cards.myCards

import android.app.Activity
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdAppWall
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdContentStream
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdFedd
import com.appodeal.ads.Appodeal
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

class CardsListAdapter(data: List<ListItem>?, var activity: Activity) : BaseMultiItemQuickAdapter<CardsListAdapter.ListItem, BaseViewHolder>(data){

    init {
        addItemType(ListItem.CARD_ITEM, R.layout.item_card)
        addItemType(ListItem.ADS_FEED_ITEM, R.layout.item_ads_feed)
        addItemType(ListItem.ADS_CONTENT_STREAM_ITEM, R.layout.item_ads_content_stream)
        addItemType(ListItem.ADS_APP_WALL_ITEM, R.layout.item_ads_app_wall)
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

            ListItem.ADS_FEED_ITEM ->{
                val ads = Appodeal.getNativeAds(1)
                if(ads.size>0){
                    helper.setNativeAdFedd(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ListItem.ADS_CONTENT_STREAM_ITEM ->{
                val ads = Appodeal.getNativeAds(1)
                if(ads.size>0){
                    helper.setNativeAdContentStream(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ListItem.ADS_APP_WALL_ITEM ->{
                val ads = Appodeal.getNativeAds(1)
                if(ads.size>0){
                    helper.setNativeAdAppWall(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
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
            const val ADS_FEED_ITEM = 1
            const val ADS_CONTENT_STREAM_ITEM = 2
            const val ADS_APP_WALL_ITEM = 3
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
                list.add(objectToItem(getEmptyCard(),ListItem.ADS_APP_WALL_ITEM))
            }

            return list
        }

        private fun getEmptyCard() : CardVO{
            return CardVO("", "")
        }
    }
}