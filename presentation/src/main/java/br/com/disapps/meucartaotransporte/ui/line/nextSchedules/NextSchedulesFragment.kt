package br.com.disapps.meucartaotransporte.ui.line.nextSchedules

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.common.BasePageAdapter
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay.NextSchedulesDayFragment
import br.com.disapps.meucartaotransporte.util.getDayName
import br.com.disapps.meucartaotransporte.util.getDayWeek
import kotlinx.android.synthetic.main.fragment_next_schedules.*
import org.koin.android.architecture.ext.viewModel

class NextSchedulesFragment : BaseFragment(){

    init {
        hasTabs = true
    }

    override val viewModel by viewModel<NextSchedulesViewModel>()
    override val fragmentLayout = R.layout.fragment_next_schedules
    private val lineViewModel  by viewModel<LineViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSchedulesDays(lineViewModel.line.code)
    }

    private fun observeViewModel() {

        viewModel.days.observe(this, Observer { daysList->
            daysList?.let {

                val adapter = BasePageAdapter(childFragmentManager)

                daysList.forEach { day ->

                    val dayFragment = NextSchedulesDayFragment.newInstance(day)

                    when (day) {
                        1 -> adapter.addFragment(dayFragment, getDayName(context!!,day))
                        2 -> adapter.addFragment(dayFragment,  getDayName(context!!,day))
                        else -> adapter.addFragment(dayFragment, getDayName(context!!,day))
                    }
                }

                view_pager.adapter = adapter
                view_pager.currentItem = getDayWeek()
                iAppActivityListener.setupTabs(view_pager)

            }
        })
    }

    companion object {
        fun newInstance() = NextSchedulesFragment()
    }
}