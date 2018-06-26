package br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection

import android.app.Activity
import br.com.disapps.domain.model.BusStop
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.util.getBusColor
import br.com.disapps.meucartaotransporte.util.loadAdIfIsPro
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.gms.ads.AdView

class ItineraryDirectionListAdapter(data : List<ListItem>?,val activity: Activity,private val lineColor:String) : BaseMultiItemQuickAdapter<ItineraryDirectionListAdapter.ListItem, BaseViewHolder>( data){

    init {
        addItemType(ListItem.BUS_STOP_ITEM, R.layout.item_itinerary)
        addItemType(ListItem.ADS_BANNER, R.layout.ad_banner)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {
        when(item.type) {
            ListItem.ADS_BANNER ->{
                (helper.itemView as AdView).loadAdIfIsPro()
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
            const val ADS_BANNER = 1
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
                if(App.instance!= null && !App.instance!!.preferences.getIsProSync()) {
                    if (i % 2 == 0) {
                        list.add(objectToItem(getEmptyBusStop(), ListItem.ADS_BANNER))
                    }
                }
                i++
            }

            return list
        }

        private fun getEmptyBusStop() : BusStop{
            return BusStop("", "", "", "", "",0,"", "")
        }
    }
}