package br.com.disapps.meucartaotransporte.ui.club.promotions

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.viewModel

class PromotionsFragment : BaseFragment(){

    override val viewModel: PromotionsViewModel  by viewModel()
    override val fragmentLayout = R.layout.fragment_recycler
    override val fragmentTag = "PromotionsFragment"

    companion object {
        fun newInstance() = PromotionsFragment()
    }
}