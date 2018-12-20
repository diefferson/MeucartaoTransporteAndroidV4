package br.com.disapps.meucartaotransporte.ui.cards.extract

import android.app.Activity
import br.com.disapps.domain.model.Extract
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.util.loadAdIfIsPro
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.android.gms.ads.AdView

class ExtractListAdapter(data: List<ListItem>,var activity: Activity) : BaseMultiItemQuickAdapter<ExtractListAdapter.ListItem, BaseViewHolder>(data){

    init {
        addItemType(ListItem.EXTRACT_ITEM, R.layout.item_extract)
        addItemType(ListItem.ADS_BANNER, R.layout.ad_banner)
    }

    override fun convert(helper: BaseViewHolder, item: ListItem) {

        when(item.type){

            ListItem.EXTRACT_ITEM ->{
                helper.setText(R.id.extract_balance,  mContext.getString(R.string.card_balance_value,String.format("%.2f",item.extract.balance)))
                helper.setText(R.id.extract_date, item.extract.date)
                helper.setText(R.id.extract_place, item.extract.local)
                helper.setText(R.id.extract_value,  mContext.getString(R.string.card_balance_value,String.format("%.2f",item.extract.value)))
            }

            ListItem.ADS_BANNER ->{
                (helper.itemView as AdView).loadAdIfIsPro()
            }
        }
    }

    class ListItem(extractItem: Extract, typeItem : Int) : MultiItemEntity {

        var extract : Extract = extractItem
        var type : Int = typeItem

        override fun getItemType() = type

        companion object {
            const val EXTRACT_ITEM = 0
            const val ADS_BANNER = 1
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
                if(App.instance!= null && !App.instance!!.preferences.getIsProSync()) {
                    if (i % 2 == 0) {
                        list.add(objectToItem(getEmptyExtract(), ListItem.ADS_BANNER))
                    }
                }
                i++
            }

            return list
        }

        private fun getEmptyExtract() : Extract{
            return Extract(0.0, "", "", 0.0)
        }
    }
}