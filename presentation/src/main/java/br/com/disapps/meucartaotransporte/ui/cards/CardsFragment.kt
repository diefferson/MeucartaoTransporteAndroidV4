package br.com.disapps.meucartaotransporte.ui.cards

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class CardsFragment : BaseFragment(){

    companion object {
        fun newInstance() = CardsFragment()
    }

    override val viewModel: CardsViewModel
        get() =  getViewModel()

    override val fragmentTag: String
        get() = CardsFragment::class.java.simpleName

    override val fragmentName: String
        get() = getString(R.string.cards)

    override val fragmentLayout: Int
        get() = R.layout.fragment_cards
}