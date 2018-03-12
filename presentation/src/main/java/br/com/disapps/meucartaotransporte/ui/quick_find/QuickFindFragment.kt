package br.com.disapps.meucartaotransporte.ui.quick_find

import android.arch.lifecycle.ViewModel
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

    override val fragmentTag: String
        get() = QuickFindFragment::class.java.simpleName

    override val fragmentName: String
        get() = getString(R.string.quick_find)

    override val fragmentLayout: Int
        get() = R.layout.fragment_quick_find
}