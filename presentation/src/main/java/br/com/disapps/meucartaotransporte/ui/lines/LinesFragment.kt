package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
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
//        scroll_view.isFillViewport = true

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLines()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_lines, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId
        when (id) {
            R.id.action_search_lines -> {
                iAppActivityListener.animateSearchAction()
               // SearchViewActivity.launch(context!!)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeViewModel(){
        viewModel.hasFavorite.observe(this, Observer {
            val adapter = LinesPageAdapter(childFragmentManager, context!!, it!= null && it)
            view_pager.adapter = adapter
            iAppActivityListener.setupTabs(view_pager)
        })
    }

    companion object {
        fun newInstance() = LinesFragment()
    }

}