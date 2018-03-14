package br.com.disapps.meucartaotransporte.ui.itineraries

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.getViewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ItineariesFragment : BaseFragment() {

    companion object {
        fun newInstance() = ItineariesFragment()
    }

    override val viewModel: ItinerariesViewModel
        get() = getViewModel()

    override val fragmentLayout: Int
        get() = R.layout.fragment_itineraries
}