package br.com.disapps.meucartaotransporte.ui.line.itineraries

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ItineariesFragment : BaseFragment() {

    companion object {
        fun newInstance() = ItineariesFragment()
    }

    override val viewModel by viewModel<ItinerariesViewModel>()

    override val fragmentLayout = R.layout.fragment_itineraries
}