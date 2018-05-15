package br.com.disapps.meucartaotransporte.ui.line.itineraries

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BasePageAdapter
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection.ItineraryDirectionFragment
import br.com.disapps.meucartaotransporte.util.getCity
import br.com.disapps.meucartaotransporte.util.getDownloadDataView
import br.com.disapps.meucartaotransporte.util.getEmptyView
import kotlinx.android.synthetic.main.fragment_itineraries.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ItinerariesFragment : BaseFragment() {

    init {
        hasTabs = true
    }

    override val viewModel by viewModel<ItinerariesViewModel>()
    override val fragmentLayout = R.layout.fragment_itineraries
    private val lineViewModel  by viewModel<LineViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getIsDownloaded(getCity(lineViewModel.line.category))
    }

    private fun observeViewModel() {

        viewModel.isDownloaded.observe(this, Observer {
            it?.let {
                if(it){
                    viewModel.getItineraryDirections(lineViewModel.line.code)
                }else{
                    error_view?.addView(activity?.getDownloadDataView())
                    error_view.visibility = View.VISIBLE
                    iAppActivityListener.hideTabs()
                }
            }
        })

        viewModel.itineraryDirections.observe(this, Observer { directions->
            if(directions != null && directions.isNotEmpty()){
                val adapter = BasePageAdapter(childFragmentManager)
                directions.forEach { direction ->
                    val dayFragment = ItineraryDirectionFragment.newInstance(direction)
                    adapter.addFragment(dayFragment, getString(R.string.direction, direction))
                }

                view_pager.adapter = adapter
                iAppActivityListener.setupTabs(view_pager)
            }else{
                error_view?.addView(activity?.getEmptyView(getString(R.string.no_itinerary_data)))
                error_view.visibility = View.VISIBLE
                iAppActivityListener.hideTabs()
            }
        })
    }

    companion object {
        fun newInstance() = ItinerariesFragment()
    }

}