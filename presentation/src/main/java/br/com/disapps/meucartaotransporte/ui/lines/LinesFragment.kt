package br.com.disapps.meucartaotransporte.ui.lines

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_cards.*
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class LinesFragment : BaseFragment() {

    companion object {
        fun newInstance() = LinesFragment()
         var teste = App.instance?.getString(R.string.lines)
    }

    override val viewModel: LinesViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_lines

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.adapter = LinesPageAdapter(childFragmentManager, context!!)
        tabs.setupWithViewPager(view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_linhas, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}