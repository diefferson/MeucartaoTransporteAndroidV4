package br.com.disapps.meucartaotransporte.ui.myCards

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 14/03/2018.
 */
class MyCardsFragment : BaseFragment(){

    companion object {
        fun newInstance() = MyCardsFragment()
    }

    override val viewModel: MyCardsViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_my_cards
}