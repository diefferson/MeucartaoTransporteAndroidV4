package br.com.disapps.meucartaotransporte.ui.lines.allLines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineActivity
import br.com.disapps.meucartaotransporte.ui.lines.LinesListAdapter
import br.com.disapps.meucartaotransporte.ui.lines.LinesViewModel
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import br.com.disapps.meucartaotransporte.util.getEmptyView
import br.com.disapps.meucartaotransporte.util.getLoadingView
import kotlinx.android.synthetic.main.fragment_list_lines.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 14/03/2018.
 */
class AllLinesFragment : BaseFragment() {

    override val viewModel by viewModel<LinesViewModel>()
    override val fragmentLayout = R.layout.fragment_list_lines
    private val mainViewModel by viewModel<MainViewModel>()
    override val fragmentTag = "AllLinesFragment"

    private val adapter:LinesListAdapter by lazy{
        LinesListAdapter(viewModel.lines, activity!!).apply {
            setOnItemChildClickListener { _, view, position ->
                when(view.id){
                    R.id.fav_line -> { viewModel.favoriteLine(getLine(position)) }
                }
            }
            setOnItemClickListener { _, view, position ->
                LineActivity.launch(context!!, parentFragment, getLine(position).line, view.findViewById(R.id.roundedImage))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        observeViewModel()
    }

    private fun initRecycler() {
        lines_recycler.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            adapter = this@AllLinesFragment.adapter
        }
    }

    private fun observeViewModel(){

        viewModel.isUpdatedLines.observe(this, Observer {
            adapter.apply {
                emptyView = activity.getEmptyView(getString(R.string.no_results))
                notifyDataSetChanged()
            }
        })

        mainViewModel.onSearchAction.observe(this, Observer {
            adapter.emptyView = activity?.getEmptyView(getString(R.string.no_results))
            if(it!= null && it){
                viewModel.linesFiltered.clear()
                viewModel.linesFiltered.addAll(viewModel.lines)
                adapter.setNewData(viewModel.linesFiltered)
            }else{
                adapter.setNewData(viewModel.lines)
            }
        })

        mainViewModel.searchText.observe(this, Observer {
            if(it!= null){
                viewModel.filterLines(it)
                adapter.apply {
                    emptyView = activity.getEmptyView(getString(R.string.no_results))
                    notifyDataSetChanged()
                }
            }
        })
    }

    override fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null && it){
                adapter.emptyView = activity?.getLoadingView()
            }
        })
    }

    companion object {
        fun newInstance()= AllLinesFragment()
    }
}