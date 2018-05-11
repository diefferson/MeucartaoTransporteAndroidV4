package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.PeriodUpdate
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

    private val periodUpdateItems :Array<String> by lazy {
        getPeriodItems(this@DataUsageActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()

        period_update_lines.setOnClickListener { periodLines() }
        period_update_schedules.setOnClickListener { periodSchedules() }
        update_line.setOnClickListener { UpdateLinesService.startService(this, true) }
        update_schedule.setOnClickListener { UpdateSchedulesService.startService(this, true)}
        update_cwb_shapes.setOnClickListener { UpdateShapesService.startService(this, City.CWB, true)}
        update_met_shapes.setOnClickListener { UpdateShapesService.startService(this, City.MET, true)}
        update_cwb_itineraries.setOnClickListener { UpdateItinerariesService.startService(this, City.CWB, true)}
        update_met_itineraries.setOnClickListener { UpdateItinerariesService.startService(this, City.MET, true)}
    }

    private fun periodLines() {
        val position = getPeriodPosition(this, viewModel.periodLines.value)
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.update_lines))
            setPositiveButton("OK",null )
            setSingleChoiceItems(periodUpdateItems, position,{ _, item ->
                viewModel.savePeriodUpdateLines(PeriodUpdate.getPeriodUpdate(item))
            })
        }.show()
    }

    private fun periodSchedules() {
        val position = getPeriodPosition(this, viewModel.periodSchedules.value)
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.update_schedules))
            setPositiveButton("OK",null)
            setSingleChoiceItems(periodUpdateItems,position, { _, item ->
                viewModel.savePeriodUpdateSchedules(PeriodUpdate.getPeriodUpdate(item))
            })
        }.show()
    }

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, DataUsageActivity::class.java))
        }
    }
}