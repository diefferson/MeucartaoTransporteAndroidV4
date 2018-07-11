package br.com.disapps.meucartaotransporte.ui.club.promotions

import br.com.disapps.domain.model.ClubPromotion
import br.com.disapps.meucartaotransporte.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class PromotionsListAdapter(data:List<ClubPromotion>) : BaseQuickAdapter<ClubPromotion, BaseViewHolder>(R.layout.item_promotion, data){

    override fun convert(helper: BaseViewHolder, item: ClubPromotion) {
        helper.setText(R.id.name, item.name)
        helper.setText(R.id.description, item.descrition)
    }
}