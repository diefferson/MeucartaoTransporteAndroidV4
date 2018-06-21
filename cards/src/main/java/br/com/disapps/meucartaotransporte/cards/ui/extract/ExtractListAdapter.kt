package br.com.disapps.meucartaotransporte.cards.ui.extract

import android.app.Activity
import br.com.disapps.domain.model.Extract
import br.com.disapps.meucartaotransporte.R as RBase
import br.com.disapps.meucartaotransporte.cards.R
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

class ExtractListAdapter(data: List<ListItem>,var activity: Activity) : BaseMultiItemQuickAdapter<ExtractListAdapter.ListItem, BaseViewHolder>(data){

    init {
        addItemType(ListItem.EXTRACT_ITEM, R.layout.item_extract)
        addItemType(ListItem.ADS_FEED_ITEM, RBase.layout.item_ads_feed)
        addItemType(ListItem.ADS_CONTENT_STREAM_ITEM, RBase.layout.item_ads_content_stream)
        addItemType(ListItem.ADS_APP_WALL_ITEM, RBase.layout.item_ads_app_wall)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {

        when(item.type){

            ListItem.EXTRACT_ITEM ->{
                helper.setText(R.id.extract_balance,  mContext.getString(RBase.string.card_balance_value,String.format("%.2f",item.extract.balance)))
                helper.setText(R.id.extract_date, item.extract.date)
                helper.setText(R.id.extract_place, item.extract.local)
                helper.setText(R.id.extract_value,  mContext.getString(RBase.string.card_balance_value,String.format("%.2f",item.extract.value)))
            }

            ListItem.ADS_FEED_ITEM ->{
//                val ads = Appodeal.getNativeAds(1)
//                if(ads.size>0){
//                    helper.setNativeAdFedd(R.id.ads_item, ads[0])
//                    Appodeal.cache(activity, Appodeal.NATIVE)
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

    class ListItem(extractItem: Extract, typeItem : Int) : MultiItemEntity {

        var extract : Extract = extractItem
        var type : Int = typeItem

        override fun getItemType() = type

        companion object {
            const val EXTRACT_ITEM = 0
            const val ADS_FEED_ITEM = 1
            const val ADS_CONTENT_STREAM_ITEM = 2
            const val ADS_APP_WALL_ITEM = 3
        }
    }

    companion object {

        fun objectToItem(extractItem: Extract, typeItem: Int): ListItem {
            return ListItem(extractItem, typeItem)
        }

        fun objectToItem(extract :List<Extract>?) : List<ListItem>{

            val list = ArrayList<ListItem>()
            var i = 0

            extract?.forEach {
                list.add(objectToItem(it,ListItem.EXTRACT_ITEM))
                if(i%2 == 0){
                    list.add(objectToItem(getEmptyExtract(),ListItem.ADS_APP_WALL_ITEM))
                }
                i++
            }

            if(list.size >0){
                list.add(objectToItem(getEmptyExtract(), ListItem.ADS_CONTENT_STREAM_ITEM))
            }

            return list
        }

        private fun getEmptyExtract() : Extract{
            return Extract(0.0, "", "", 0.0)
        }
    }
}