package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.ViewModel
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class LinesFragment : BaseFragment() {

    companion object {
        fun newInstance() = LinesFragment()
    }

    override val viewModel: LinesViewModel
        get() = getViewModel()

    override val fragmentTag: String
        get() = LinesFragment::class.java.simpleName

    override val fragmentName: String
        get() = getString(R.string.lines)

    override val fragmentLayout: Int
        get() = R.layout.fragment_lines
}