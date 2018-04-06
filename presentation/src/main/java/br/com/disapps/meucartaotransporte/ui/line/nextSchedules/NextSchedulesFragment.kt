package br.com.disapps.meucartaotransporte.ui.line.nextSchedules

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import org.koin.android.architecture.ext.viewModel

class NextSchedulesFragment : BaseFragment(){

    override val viewModel by viewModel<NextSchedulesViewModel>()

    override val fragmentLayout = R.layout.fragment_next_schedules

    companion object {
        fun newInstance() = NextSchedulesFragment()
    }
}