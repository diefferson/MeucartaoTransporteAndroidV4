package br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.util.getEmptyView
import br.com.disapps.meucartaotransporte.util.getLoadingView
import kotlinx.android.synthetic.main.fragment_itinerary_direction.*
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel

class ItineraryDirectionFragment : BaseFragment(){

    override val viewModel by viewModel<ItineraryDirectionViewModel>()
    override val fragmentLayout = R.layout.fragment_itinerary_direction
    private val lineViewModel  by sharedViewModel<LineViewModel>()
    override val fragmentTag = "ItineraryDirectionFragment"

    private val adapter : ItineraryDirectionListAdapter by lazy {
        ItineraryDirectionListAdapter(ArrayList(),activity!!, lineViewModel.line.color)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            viewModel.getItinerary(lineViewModel.line.code, it.getString(DIRECTION))
        }
    }

    private fun initRecyclerView() {
        itinerary_recycler.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            adapter = this@ItineraryDirectionFragment.adapter
        }
    }

    private fun observeViewModel(){
        viewModel.itinerary.observe(this, Observer {
            if(it!= null && it.isNotEmpty()){
                adapter.setNewData(ItineraryDirectionListAdapter.objectToItem(it))
                hideErrorView()
            }else{
                showErrorView(activity?.getEmptyView(getString(R.string.no_itinerary_data)))
            }
        })
    }

    override fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null && it){
                showErrorView(activity?.getLoadingView())
            }
        })
    }

    private fun showErrorView(view :View?){
        error_view.removeAllViews()
        error_view?.addView(view)
        error_view.visibility = View.VISIBLE
    }

    private fun hideErrorView(){
        error_view.removeAllViews()
        error_view.visibility = View.GONE
    }

    companion object {
        private const val DIRECTION = "direction"
        fun newInstance(direction : String) = ItineraryDirectionFragment().apply {
            arguments = Bundle().apply {
                putString(DIRECTION, direction)
            }
        }
    }
}