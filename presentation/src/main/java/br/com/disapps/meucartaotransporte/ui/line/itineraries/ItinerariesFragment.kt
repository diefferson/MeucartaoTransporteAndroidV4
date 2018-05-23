package br.com.disapps.meucartaotransporte.ui.line.itineraries

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import android.widget.Button
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.services.UpdateItinerariesService
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BasePageAdapter
import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.AnimatedCircleLoadingView
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection.ItineraryDirectionFragment
import br.com.disapps.meucartaotransporte.util.getCity
import br.com.disapps.meucartaotransporte.util.getDownloadDataView
import br.com.disapps.meucartaotransporte.util.getEmptyView
import br.com.disapps.meucartaotransporte.util.getProgressView
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
                    error_view.visibility = View.GONE
                    error_view.removeAllViews()
                    viewModel.getItineraryDirections(lineViewModel.line.code)
                }else{
                    error_view?.addView(activity?.getDownloadDataView())
                    error_view.visibility = View.VISIBLE
                    error_view.findViewById<Button>(R.id.download).setOnClickListener {
                        if( lineViewModel.line.category == "METROPOLITANA"){
                            UpdateItinerariesService.startService(context!!, City.MET, true, viewModel.updateProgressListener)
                        }else{
                            UpdateItinerariesService.startService(context!!, City.CWB, true, viewModel.updateProgressListener)
                        }
                        downloadData()
                    }

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

    private fun downloadData(){
        error_view.removeAllViews()
        error_view?.addView(activity?.getProgressView())
        error_view.visibility = View.VISIBLE
        val progress = error_view.findViewById<AnimatedCircleLoadingView>(R.id.progress)
        progress.startDeterminate()
        viewModel.downloadProgress.observe(this, Observer {
            it?.let{
                progress.setPercent(it)
            }
        })
    }

    companion object {
        fun newInstance() = ItinerariesFragment()
    }

}