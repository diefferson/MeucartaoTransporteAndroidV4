package br.com.disapps.meucartaotransporte.ui.shapes

import android.arch.lifecycle.ViewModel
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ShapesFragment : BaseFragment() {

    companion object {
        fun newInstance() = ShapesFragment()
    }

    override val viewModel: ViewModel
        get() = getViewModel()

    override val fragmentTag: String
        get() = ShapesFragment::class.java.simpleName

    override val fragmentName: String
        get() = getString(R.string.shapes)

    override val fragmentLayout: Int
        get() = R.layout.fragment_shapes
}