package br.com.disapps.meucartaotransporte.ui.lines.shapes

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ShapesFragment : BaseFragment() {

    override val viewModel: ShapesViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_shapes

    companion object {
        fun newInstance() = ShapesFragment()
    }
}