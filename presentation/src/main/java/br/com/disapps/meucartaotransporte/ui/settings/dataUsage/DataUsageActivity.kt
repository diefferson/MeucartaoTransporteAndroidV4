package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.services.UpdateItinerariesService
import br.com.disapps.meucartaotransporte.services.UpdateLinesService
import br.com.disapps.meucartaotransporte.services.UpdateSchedulesService
import br.com.disapps.meucartaotransporte.services.UpdateShapesService
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import br.com.disapps.meucartaotransporte.util.getPeriodItems
import br.com.disapps.meucartaotransporte.util.getPeriodPosition
import kotlinx.android.synthetic.main.activity_data_usage.*
import org.koin.android.architecture.ext.viewModel

class DataUsageActivity : BaseActivity(){

    override val viewModel by viewModel<DataUsageViewModel>()
    override val activityLayout = R.layout.activity_data_usage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()

        period_update_lines.setOnClickListener { periodLines() }
        period_update_schedules.setOnClickListener { periodSchedules() }

        update_line.setOnClickListener { UpdateLinesService.startService(this) }
        update_schedule.setOnClickListener { UpdateSchedulesService.startService(this)}
        update_cwb_shapes.setOnClickListener { UpdateShapesService.startService(this, City.CWB)}
        update_met_shapes.setOnClickListener { UpdateShapesService.startService(this, City.MET)}
        update_cwb_itineraries.setOnClickListener { UpdateItinerariesService.startService(this, City.CWB)}
        update_met_itineraries.setOnClickListener { UpdateItinerariesService.startService(this, City.MET)}

    }

    private fun periodLines() {
        val period = "semanal"
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.update_lines))
            setSingleChoiceItems(getPeriodItems(this@DataUsageActivity), getPeriodPosition(this@DataUsageActivity, period), { d, n ->  })
            setPositiveButton("OK", { _, _ -> period_update_lines_value.text = period } )
        }.show()
    }

    private fun periodSchedules() {
        val period = "semanal"
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.update_schedules))
            setSingleChoiceItems(getPeriodItems(this@DataUsageActivity), getPeriodPosition(this@DataUsageActivity, period), { d, n ->  })
            setPositiveButton("OK", { _, _ -> period_update_schedules_value.text = period })
        }.show()
    }

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, DataUsageActivity::class.java))
        }
    }
}