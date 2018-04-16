package br.com.disapps.meucartaotransporte.ui.lines.allLines

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineActivity
import br.com.disapps.meucartaotransporte.ui.lines.LinesListAdapter
import br.com.disapps.meucartaotransporte.ui.lines.LinesViewModel
import br.com.disapps.meucartaotransporte.ui.main.MainViewModel
import br.com.disapps.meucartaotransporte.util.extensions.inflateView
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_list_lines.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 14/03/2018.
 */
class AllLinesFragment : BaseFragment() {

    override val viewModel by viewModel<LinesViewModel>()
    override val fragmentLayout = R.layout.fragment_list_lines
    private val mainViewModel by viewModel<MainViewModel>()

    private val listAdapter:LinesListAdapter by lazy{
        LinesListAdapter(viewModel.lines).apply {

            emptyView = activity?.inflateView(R.layout.loading_view, lines_recycler )
            openLoadAnimation(BaseQuickAdapter.ALPHAIN)

            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.fav_line -> { viewModel.favoriteLine(adapter.data[position] as LineVO) }
                }
            }

            setOnItemClickListener { adapter, view, position ->
                LineActivity.launch(context!!, adapter.data[position] as LineVO, view.findViewById(R.id.roundedImage))
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
            adapter = this@AllLinesFragment.listAdapter
        }
    }

    private fun observeViewModel(){

        viewModel.isUpdatedLines.observe(this, Observer {
            listAdapter.apply {
                emptyView = activity?.inflateView(R.layout.empty_view, lines_recycler )
                notifyDataSetChanged()
            }
        })

        mainViewModel.onSearchAction.observe(this, Observer {
            listAdapter.emptyView = activity?.inflateView(R.layout.empty_view, lines_recycler )
            if(it!= null && it){
                viewModel.linesFiltered.clear()
                viewModel.linesFiltered.addAll(viewModel.lines)
                listAdapter.setNewData(viewModel.linesFiltered)
            }else{
                listAdapter.setNewData(viewModel.lines)
            }
        })

        mainViewModel.searchText.observe(this, Observer {
            if(it!= null){
                viewModel.filterLines(it)
                listAdapter.apply {
                    emptyView = activity?.inflateView(R.layout.empty_view, lines_recycler )
                    notifyDataSetChanged()
                }
            }
        })
    }

    companion object {
        fun newInstance()= AllLinesFragment()
    }
}