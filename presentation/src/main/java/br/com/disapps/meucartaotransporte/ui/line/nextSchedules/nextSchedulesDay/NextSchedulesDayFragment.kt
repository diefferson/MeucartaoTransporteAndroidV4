package br.com.disapps.meucartaotransporte.ui.line.nextSchedules.nextSchedulesDay

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.SchedulesDetail
import br.com.disapps.meucartaotransporte.ui.common.BaseFragment
import br.com.disapps.meucartaotransporte.ui.line.LineViewModel
import br.com.disapps.meucartaotransporte.ui.schedules.SchedulesActivity
import br.com.disapps.meucartaotransporte.util.getEmptyView
import br.com.disapps.meucartaotransporte.util.getLoadingView
import kotlinx.android.synthetic.main.fragment_recycler.*
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel

class NextSchedulesDayFragment : BaseFragment(){

    override val viewModel by viewModel<NextSchedulesDayViewModel>()
    override val fragmentLayout = R.layout.fragment_recycler
    private val lineViewModel  by sharedViewModel<LineViewModel>()
    override val fragmentTag = "NextScheduleDayListAdapter"

    private val adapter:NextScheduleDayListAdapter by lazy {
        NextScheduleDayListAdapter(ArrayList(), activity!!).apply {
            setOnItemClickListener { _, _, position ->
                if(adapter.getItem(position)!!.type == NextScheduleDayListAdapter.ListItem.LINE_SCHEDULE_ITEM){
                    SchedulesActivity.launch(context!!, SchedulesDetail(
                            lineCode = getLineSchedule(position).lineCode,
                            day =  getLineSchedule(position).day,
                            busStopName = getLineSchedule(position).busStopName,
                            busStopCode = getLineSchedule(position).busStopCode,
                            lineColor = lineViewModel.line.color
                    ))
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            viewModel.getNextSchedules(lineViewModel.line.code, it.getInt(DAY))
        }
    }

    private fun initRecyclerView() {
        recycler.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            adapter = this@NextSchedulesDayFragment.adapter
        }
    }

    private fun observeViewModel(){
        viewModel.nextSchedules.observe(this, Observer {
            if(it!= null && it.isNotEmpty()){
                adapter.setNewData(NextScheduleDayListAdapter.objectToItem(it))
                hideErrorView()
            }else{
                showErrorView(activity?.getEmptyView(getString(R.string.no_schedule_data)))
            }
        })
    }

    private fun showErrorView(view :View?){
        error_view.removeAllViews()
        error_view?.addView(view)
        error_view.visibility = View.VISIBLE
    }

    private fun hideErrorView(){
        error_view.removeAllViews()
        error_view.visibility = View.GONE
    }

    override fun setupLoading() {
        viewModel.getIsLoadingObservable().observe(this, Observer {
            if(it!= null && it){
                showErrorView(activity?.getLoadingView())
            }
        })
    }

    companion object {
        private const val DAY = "day"
        fun newInstance(day: Int) = NextSchedulesDayFragment().apply {
            arguments = Bundle().apply {
                putInt(DAY, day)
            }
        }
    }
}