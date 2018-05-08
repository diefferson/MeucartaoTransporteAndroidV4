package br.com.disapps.meucartaotransporte.ui.schedules

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.SchedulesDetail
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.extensions.inflateView
import br.com.disapps.meucartaotransporte.util.getAdViewContentStream
import br.com.disapps.meucartaotransporte.util.getCustomTheme
import br.com.disapps.meucartaotransporte.util.getDayName
import kotlinx.android.synthetic.main.activity_balance.*
import kotlinx.android.synthetic.main.activity_schedules.*
import kotlinx.android.synthetic.main.include_toolbar_schedules.*
import org.koin.android.architecture.ext.viewModel

class SchedulesActivity : BaseActivity (){

    override val viewModel by viewModel<SchedulesViewModel>()

    override val activityLayout = R.layout.activity_schedules

    private lateinit var schedulesDetail : SchedulesDetail

    private val listAdapter : SchedulesListAdapter by lazy {
        SchedulesListAdapter(ArrayList(), 0).apply {
            emptyView = inflateView(R.layout.loading_view, schedules_recycler )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        schedulesDetail = intent.getSerializableExtra(SCHEDULES_DETAIL) as SchedulesDetail
        setTheme(getCustomTheme(schedulesDetail.lineColor))
        super.onCreate(savedInstanceState)

        setSupportActionBar(vToolbar)
        day.text = getDayName(this, schedulesDetail.day)
        pointer.text = schedulesDetail.busStopName

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSchedules(schedulesDetail.lineCode, schedulesDetail.day, schedulesDetail.busStopCode)
    }

    private fun initRecyclerView() {
        schedules_recycler.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = this@SchedulesActivity.listAdapter.apply {
                day = schedulesDetail.day
            }
        }
    }

    private fun observeViewModel(){
        viewModel.schedules.observe(this, Observer {
            listAdapter.apply {
                emptyView = inflateView(R.layout.empty_view, schedules_recycler )
                setFooterView(getAdViewContentStream(schedules_recycler))
                setNewData(it)
            }
        })
    }

    companion object {
        private const val SCHEDULES_DETAIL = "schedulesDetail"
        fun launch(context: Context, schedulesDetail: SchedulesDetail){
            context.startActivity(Intent(context, SchedulesActivity::class.java).apply {
                putExtra(SCHEDULES_DETAIL, schedulesDetail)
            })
        }
    }
}