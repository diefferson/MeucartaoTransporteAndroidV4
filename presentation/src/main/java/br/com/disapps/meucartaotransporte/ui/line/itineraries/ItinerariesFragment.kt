package br.com.disapps.meucartaotransporte.ui.line.itineraries

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.LineVO
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.viewModel

/**
 * Created by dnso on 12/03/2018.
 */
class ItinerariesFragment : BaseFragment() {

    override val viewModel by viewModel<ItinerariesViewModel>()

    override val fragmentLayout = R.layout.fragment_itineraries

    companion object {
        fun newInstance() = ItinerariesFragment()
    }

}