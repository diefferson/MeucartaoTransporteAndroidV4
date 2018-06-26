package br.com.disapps.meucartaotransporte.ui.lines

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineActivity
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import br.com.disapps.meucartaotransporte.util.getLoadingView
import kotlinx.android.synthetic.main.fragment_lines.*
import org.koin.android.architecture.ext.sharedViewModel

class LinesFragment : BaseFragment() {

    init {
        hasTabs = true
    }

    override val viewModel by sharedViewModel<LinesViewModel>()
    override val fragmentLayout = R.layout.fragment_lines
    private val mainViewModel by sharedViewModel<MainViewModel>()
    override val fragmentTag = "LinesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        iAppActivityListener.setTitle(getString(R.string.app_name))
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
            R.id.action_search_lines -> iAppActivityListener.animateSearchAction()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == LineActivity.REQUEST_ID){
            if(data != null && data.hasExtra(LineActivity.LINE_CODE) ){
                val lineCode  = data.getStringExtra(LineActivity.LINE_CODE)
                viewModel.updateFavoriteLine(lineCode, resultCode == LineActivity.RESULT_LINE_FAVOR)
            }
        }
    }

    private fun observeViewModel(){
        viewModel.hasFavorite.observe(this, Observer {
            view_pager.adapter = LinesPageAdapter(childFragmentManager, context!!, it!= null && it)
            iAppActivityListener.setupTabs(view_pager)
        })

        mainViewModel.onSearchAction.observe(this, Observer {
            if(it!= null && it){
                if(view_pager.adapter != null){
                    if((view_pager.adapter as LinesPageAdapter).hasFavorite){
                        view_pager.currentItem = 1
                    }else{
                        view_pager.currentItem = 0
                    }
                }
            }
        })
    }

    override fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null &&  it && !viewModel.isRequested){
                error_view.visibility = View.VISIBLE
                error_view.addView(activity?.getLoadingView())
            }else{
                error_view.visibility = View.GONE
                error_view.removeAllViews()
            }
        })
    }

    companion object {
        fun newInstance() = LinesFragment()
    }
}