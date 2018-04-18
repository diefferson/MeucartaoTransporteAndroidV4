package br.com.disapps.meucartaotransporte.ui.line.itineraries

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BasePageAdapter
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.ui.line.itineraries.itineraryDirection.ItineraryDirectionFragment
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
        viewModel.getItineraryDirections(lineViewModel.line.code)
    }

    private fun observeViewModel() {

        viewModel.itineraryDirections.observe(this, Observer { directions->
            directions?.let {
                val adapter = BasePageAdapter(childFragmentManager)
                directions.forEach { direction ->
                    val dayFragment = ItineraryDirectionFragment.newInstance(direction)
                    adapter.addFragment(dayFragment, getString(R.string.direction, direction))
                }

                view_pager.adapter = adapter
                iAppActivityListener.setupTabs(view_pager)
            }
        })
    }

    companion object {
        fun newInstance() = ItinerariesFragment()
    }

}