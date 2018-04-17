package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.services.updateLines.UpdateLinesService
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_data_usage.*
import org.koin.android.architecture.ext.viewModel

class DataUsageActivity : BaseActivity(){

    override val viewModel by viewModel<DataUsageViewModel>()
    override val activityLayout = R.layout.activity_data_usage
    private val items = arrayOf<CharSequence>("semanal", "mensal", "anual", "manual")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()

        period_update_lines.setOnClickListener { periodLines() }
        period_update_schedules.setOnClickListener { periodSchedules() }
        update_line.setOnClickListener { UpdatingDataActivity.launch(this, UpdateData.LINES)}
        update_schedule.setOnClickListener { UpdatingDataActivity.launch(this, UpdateData.SCHEDULES)}
        update_cwb_itineraries.setOnClickListener { UpdatingDataActivity.launch(this, UpdateData.CWB_ITINERARIES)}
        update_cwb_shapes.setOnClickListener { UpdatingDataActivity.launch(this, UpdateData.CWB_SHAPES)}
        update_met_itineraries.setOnClickListener { UpdatingDataActivity.launch(this, UpdateData.MET_ITINERARIES)}
        update_met_shapes.setOnClickListener { UpdatingDataActivity.launch(this, UpdateData.MET_SHAPES)}
    }

    private fun periodLines() {
        val adb = AlertDialog.Builder(this)
        val period = "semanal"
        adb.setSingleChoiceItems(items, getPosition(period), { d, n ->  })
        adb.setPositiveButton("OK", { dialog, which -> period_update_lines_value.text = period })
        adb.setTitle("Atualização Linhas")
        adb.show()
    }

    private fun periodSchedules() {
        val adb = AlertDialog.Builder(this)
        val period = "semanal"
        adb.setSingleChoiceItems(items, getPosition(period), { d, n ->  })
        adb.setPositiveButton("OK", { dialog, which -> period_update_schedules_value.text = period })
        adb.setTitle("Atualização Linhas")
        adb.show()
    }

    private fun getPosition(periodo: String): Int {
        return when (periodo) {
            "semanal" -> 0
            "mensal" -> 1
            "anual" -> 2
            "manual" -> 3
            else -> 0
        }
    }

    private fun getPeriod(position: Int): String {
        return when (position) {
            0 -> "semanal"
            1 -> "mensal"
            2 -> "anual"
            3 -> "manual"
            else -> ""
        }
    }

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, DataUsageActivity::class.java))
        }
    }
}