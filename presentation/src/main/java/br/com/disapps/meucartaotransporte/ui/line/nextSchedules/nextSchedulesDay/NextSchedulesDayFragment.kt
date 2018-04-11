package br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.SchedulesDetail
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.schedules.SchedulesActivity
import br.com.disapps.meucartaotransporte.util.extensions.inflateView
import kotlinx.android.synthetic.main.fragment_next_schedules_day.*
import org.koin.android.architecture.ext.getViewModel

class NextSchedulesDayFragment : BaseFragment(){

    override val viewModel : NextSchedulesDayViewModel
            get() = getViewModel()

    override val fragmentLayout = R.layout.fragment_next_schedules_day

    private val listAdapter : NextScheduleDayListAdapter by lazy {
        NextScheduleDayListAdapter(ArrayList()).apply {

            emptyView = activity?.inflateView(R.layout.empty_view, next_schedules_recycler )

            setOnItemClickListener { adapter, _, position ->
                    SchedulesActivity.launch(context!!, SchedulesDetail(
                            lineCode= (adapter.data[position] as LineSchedule).lineCode,
                            day =  (adapter.data[position] as LineSchedule).day,
                            busStopName = (adapter.data[position] as LineSchedule).busStopName,
                            busStopCode = (adapter.data[position] as LineSchedule).busStopCode
                    ))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next_schedules_recycler.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            adapter = this@NextSchedulesDayFragment.listAdapter
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            viewModel.getNextSchedules(it.getString(CODE_LINE), it.getInt(DAY))
        }
    }

    private fun observeViewModel(){
        viewModel.nextSchedules.observe(this, Observer {
                listAdapter.setNewData(it)
        })
    }

    companion object {
        private const val CODE_LINE = "codeLine"
        private const val DAY = "day"
        fun newInstance(codeLine: String, day: Int) = NextSchedulesDayFragment().apply {
            arguments = Bundle().apply {
                putString(CODE_LINE, codeLine)
                putInt(DAY, day)
            }
        }
    }
}