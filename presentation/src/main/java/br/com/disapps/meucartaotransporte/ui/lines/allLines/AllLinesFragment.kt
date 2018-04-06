package br.com.disapps.meucartaotransporte.ui.lines.allLines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineActivity
import br.com.disapps.meucartaotransporte.ui.lines.LinesAdapter
import br.com.disapps.meucartaotransporte.ui.lines.LinesViewModel
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import br.com.disapps.meucartaotransporte.util.extensions.inflateView
import kotlinx.android.synthetic.main.fragment_list_lines.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 14/03/2018.
 */
class AllLinesFragment : BaseFragment() {

    override val viewModel by viewModel<LinesViewModel>()

    override val fragmentLayout = R.layout.fragment_list_lines

    private val mainViewModel by viewModel<MainViewModel>()

    private val adapter:LinesAdapter by lazy{
        LinesAdapter(viewModel.lines).apply {
            emptyView = activity?.inflateView(R.layout.empty_view, lines_recycler )
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.fav_line -> {
                        viewModel.favoriteLine(adapter.data[position] as LineVO)
                    }
                }
            }
            setOnItemClickListener { adapter, view, position -> LineActivity.launch(context!!)  }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lines_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            adapter = this@AllLinesFragment.adapter
            itemAnimator = DefaultItemAnimator()
        }

        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.isUpdatedLines.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

        mainViewModel.onSearchAction.observe(this, Observer {
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
                adapter.notifyDataSetChanged()
            }
        })
    }

    companion object {
        fun newInstance()= AllLinesFragment()
    }
}