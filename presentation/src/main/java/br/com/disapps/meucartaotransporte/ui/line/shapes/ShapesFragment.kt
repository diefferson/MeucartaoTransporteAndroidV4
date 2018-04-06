package br.com.disapps.meucartaotransporte.ui.line.shapes

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ShapesFragment : BaseFragment() {

    override val viewModel by viewModel<ShapesViewModel>()

    override val fragmentLayout = R.layout.fragment_shapes

    companion object {
        fun newInstance() = ShapesFragment()
    }
}