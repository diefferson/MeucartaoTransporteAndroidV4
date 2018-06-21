package br.com.disapps.meucartaotransporte.cards.ui.balance

import android.app.Activity
import android.graphics.Color
import br.com.disapps.meucartaotransporte.R as RBase
import br.com.disapps.meucartaotransporte.cards.R
import br.com.disapps.meucartaotransporte.base.model.CardVO
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

class BalanceListAdapter(data : List<ListItem>,var activity: Activity) : BaseMultiItemQuickAdapter<BalanceListAdapter.ListItem, BaseViewHolder>(data){

    init {
        addItemType(ListItem.BALANCE_ITEM, R.layout.item_balance)
        addItemType(ListItem.ADS_FEED_ITEM, RBase.layout.item_ads_feed)
        addItemType(ListItem.ADS_CONTENT_STREAM_ITEM, RBase.layout.item_ads_content_stream)
        addItemType(ListItem.ADS_APP_WALL_ITEM, RBase.layout.item_ads_app_wall)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {

        when(item.type){

            ListItem.BALANCE_ITEM ->{
                helper.setText(R.id.card_status, mContext.getString(RBase.string.card_state, item.balance.status))
                helper.setText(R.id.card_type, mContext.getString(RBase.string.card_type, item.balance.type))
                helper.setText(R.id.card_balance, mContext.getString(RBase.string.card_balance_value, String.format("%.2f",item.balance.balance)))
                helper.setText(R.id.card_balance_date, mContext.getString(RBase.string.card_updated, item.balance.balanceDate))

                if(item.balance.balance >15){
                    helper.setTextColor(R.id.card_balance, Color.GREEN)
                }else{
                    helper.setTextColor(R.id.card_balance, Color.RED)
                }
            }

            ListItem.ADS_FEED_ITEM ->{
//                val ads = Appodeal.getNativeAds(1)
//                if(ads.size>0){
//                    helper.setNativeAdFedd(R.id.ads_item, ads[0])
//                   // Appodeal.cache(activity, Appodeal.NATIVE)
//                }
            }

            ListItem.ADS_CONTENT_STREAM_ITEM ->{
//                val ads = Appodeal.getNativeAds(1)
//                if(ads.size>0){
//                    helper.setNativeAdContentStream(R.id.ads_item, ads[0])
//                    Appodeal.cache(activity, Appodeal.NATIVE)
//                }
            }

            ListItem.ADS_APP_WALL_ITEM ->{
//                val ads = Appodeal.getNativeAds(1)
//                if(ads.size>0){
//                    helper.setNativeAdAppWall(R.id.ads_item, ads[0])
//                    Appodeal.cache(activity, Appodeal.NATIVE)
//                }
            }
        }
    }

    class ListItem(balanceItem: CardVO, typeItem : Int) : MultiItemEntity {

        var balance : CardVO = balanceItem
        var type : Int = typeItem

        override fun getItemType() = type

        companion object {
            const val BALANCE_ITEM = 0
            const val ADS_FEED_ITEM = 1
            const val ADS_CONTENT_STREAM_ITEM = 2
            const val ADS_APP_WALL_ITEM = 3
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

            if(list.size >0){
                list.add(objectToItem(getEmptyCard(), ListItem.ADS_CONTENT_STREAM_ITEM))
            }

            return list
        }

        private fun getEmptyCard() : CardVO {
            return CardVO( "", "")
        }
    }
}