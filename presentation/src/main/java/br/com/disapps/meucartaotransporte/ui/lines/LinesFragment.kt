package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_lines.*
import org.koin.android.architecture.ext.viewModel

class LinesFragment : BaseFragment() {

    init {
        hasTabs = true
    }

    override val viewModel by viewModel<LinesViewModel>()

    override val fragmentLayout = R.layout.fragment_lines

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        scroll_view.isFillViewport = true

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_searchable_activity, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLines()
    }

    private fun observeViewModel(){
        viewModel.hasFavorite.observe(this, Observer {
            view_pager.adapter = LinesPageAdapter(childFragmentManager, context!!, it!= null && it)
            iAppActivityListener.setupTabs(view_pager)
        })
    }

    companion object {
        fun newInstance() = LinesFragment()
    }

}