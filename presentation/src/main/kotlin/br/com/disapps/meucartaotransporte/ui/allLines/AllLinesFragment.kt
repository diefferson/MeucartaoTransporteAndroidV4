package br.com.disapps.meucartaotransporte.ui.allLines

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 14/03/2018.
 */
class AllLinesFragment : BaseFragment() {

    companion object {
        fun newInstance() = AllLinesFragment()
    }

    override val viewModel: AllLinesViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_all_lines
}