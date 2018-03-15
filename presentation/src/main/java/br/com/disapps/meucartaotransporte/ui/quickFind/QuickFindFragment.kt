package br.com.disapps.meucartaotransporte.ui.quickFind

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class QuickFindFragment: BaseFragment(){

    companion object {
        fun newInstance() = QuickFindFragment()
    }

    override val viewModel: QuickFindViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_quick_find
}