package br.com.disapps.meucartaotransporte.ui.line.nextSchedules

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay.NextSchedulesDayFragment
import kotlinx.android.synthetic.main.fragment_next_schedules.*
import org.koin.android.architecture.ext.viewModel

class NextSchedulesFragment : BaseFragment(){

    init {
        hasTabs = true
    }

    override val viewModel by viewModel<NextSchedulesViewModel>()

    override val fragmentLayout = R.layout.fragment_next_schedules

    private lateinit var codeLine : String

    private val lineViewModel  by viewModel<LineViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        codeLine = if(arguments!= null){
            arguments!!.getString(CODE_LINE)
        }else{
            lineViewModel.line.code
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSchedulesDays(codeLine)
    }

    private fun observeViewModel() {

        viewModel.days.observe(this, Observer { list->
            list?.let {
                val adapter = NextSchedulesPagerAdapter(childFragmentManager)
                list.forEach { day ->
                    val dayFragment = NextSchedulesDayFragment.newInstance(codeLine, day)
                    when (day) {
                        1 -> adapter.addFragment(dayFragment, resources.getString(R.string.useful_day))
                        2 -> adapter.addFragment(dayFragment, resources.getString(R.string.saturday))
                        else -> adapter.addFragment(dayFragment, resources.getString(R.string.sunday))
                    }
                }
                view_pager.adapter = adapter
                iAppActivityListener.setupTabs(view_pager)
            }
        })
    }

    companion object {
        private const val CODE_LINE = "codeLine"
        fun newInstance(codeLine : String) = NextSchedulesFragment().apply {
            if(!codeLine.isEmpty()){
                arguments = Bundle().apply {
                    putString(CODE_LINE, codeLine)
                }
            }
        }
    }
}