package br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection

import android.app.Activity
import br.com.disapps.domain.model.BusStop
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdAppWall
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdContentStream
import br.com.disapps.meucartaotransporte.ui.custom.setNativeAdFedd
import br.com.disapps.meucartaotransporte.util.getBusColor
import com.appodeal.ads.Appodeal
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

class ItineraryDirectionListAdapter(data : List<ListItem>?,val activity: Activity,private val lineColor:String) : BaseMultiItemQuickAdapter<ItineraryDirectionListAdapter.ListItem, BaseViewHolder>( data){

    init {
        addItemType(ListItem.BUS_STOP_ITEM, R.layout.item_itinerary)
        addItemType(ListItem.ADS_FEED_ITEM, R.layout.item_ads_feed)
        addItemType(ListItem.ADS_CONTENT_STREAM_ITEM, R.layout.item_ads_content_stream)
        addItemType(ListItem.ADS_APP_WALL_ITEM, R.layout.item_ads_app_wall)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {
        when(item.type) {
            ListItem.ADS_FEED_ITEM -> {
                val ads = Appodeal.getNativeAds(1)
                if (ads.size > 0) {
                    helper.setNativeAdFedd(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ListItem.ADS_CONTENT_STREAM_ITEM -> {
                val ads = Appodeal.getNativeAds(1)
                if (ads.size > 0) {
                    helper.setNativeAdContentStream(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ListItem.ADS_APP_WALL_ITEM -> {
                val ads = Appodeal.getNativeAds(1)
                if (ads.size > 0) {
                    helper.setNativeAdAppWall(R.id.ads_item, ads[0])
                    Appodeal.cache(activity, Appodeal.NATIVE)
                }
            }

            ListItem.BUS_STOP_ITEM -> {
                helper.setText(R.id.name, item.busStop.name)
                helper.setText(R.id.type, item.busStop.type)
                helper.setBackgroundColor(R.id.ic_it_item, getBusColor(mContext, lineColor))
            }
        }
    }

    class ListItem(busStopItem: BusStop,  typeItem : Int) : MultiItemEntity {

        var busStop : BusStop = busStopItem
        var type : Int = typeItem

        override fun getItemType() = type

        companion object {
            const val BUS_STOP_ITEM = 0
            const val ADS_FEED_ITEM = 1
            const val ADS_CONTENT_STREAM_ITEM = 2
            const val ADS_APP_WALL_ITEM = 3
        }
    }

    companion object {

        fun objectToItem(busStopItem: BusStop, typeItem: Int): ListItem {
            return ListItem(busStopItem, typeItem)
        }

        fun objectToItem(items :List<BusStop>?) : List<ListItem>{

            val list = ArrayList<ListItem>()
            var i = 0

            items?.forEach {
                list.add(objectToItem(it, ListItem.BUS_STOP_ITEM))
                if (i % 2 == 0) {
                    list.add(objectToItem(getEmptyBusStop(), ListItem.ADS_APP_WALL_ITEM))
                }
                i++
            }

            if(list.size >0){
                list.add(objectToItem(getEmptyBusStop(), ListItem.ADS_CONTENT_STREAM_ITEM))
            }

            return list
        }

        private fun getEmptyBusStop() : BusStop{
            return BusStop("", "", "", "", "",0,"", "")
        }
    }
}