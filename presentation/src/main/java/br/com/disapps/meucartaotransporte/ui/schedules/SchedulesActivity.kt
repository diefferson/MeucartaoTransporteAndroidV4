package br.com.disapps.meucartaotransporte.ui.schedules

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.SchedulesDetail
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_schedules.*
import org.koin.android.architecture.ext.viewModel

class SchedulesActivity : BaseActivity (){

    override val viewModel by viewModel<SchedulesViewModel>()

    override val activityLayout = R.layout.activity_schedules

    private val listAdapter : SchedulesListAdapter by lazy {
        SchedulesListAdapter(ArrayList(), 0)
    }

    private lateinit var schedulesDetail : SchedulesDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        schedulesDetail = intent.getSerializableExtra(SCHEDULES_DETAIL) as SchedulesDetail

        schedules_recycler.apply {
            layoutManager = GridLayoutManager(context,4)
            adapter = this@SchedulesActivity.listAdapter.apply { day = schedulesDetail.day }
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.schedules.observe(this, Observer {
            listAdapter.setNewData(it)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSchedules(schedulesDetail.lineCode, schedulesDetail.day, schedulesDetail.busStopCode)
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