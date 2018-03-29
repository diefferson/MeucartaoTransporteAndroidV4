package br.com.disapps.meucartaotransporte.ui.lines

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel
import kotlinx.android.synthetic.main.fragment_lines.*


/**
 * Created by dnso on 12/03/2018.
 */
class LinesFragment : BaseFragment() {

    companion object {
        fun newInstance() = LinesFragment()
         var teste = App.instance?.getString(R.string.lines)
    }

    init {
        hasTabs = true
    }

    override val viewModel: LinesViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_lines


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        view_pager.adapter = LinesPageAdapter(childFragmentManager, context!!)
        iAppActivityListener.setupTabs(view_pager)
        scroll_view.isFillViewport =true

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_searchable_activity, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}