package br.com.disapps.meucartaotransporte.ui.favoritesLines

import android.arch.lifecycle.ViewModel
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.databinding.FragmentFavoritesLinesBinding
import br.com.disapps.meucartaotransporte.databinding.FragmentLinesBinding
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 14/03/2018.
 */
class FavoritesLinesFragment : BaseFragment<FragmentFavoritesLinesBinding>() {

    companion object {
        fun newInstance() = FavoritesLinesFragment()
    }

    override val viewModel: FavoritesLinesViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_favorites_lines
}