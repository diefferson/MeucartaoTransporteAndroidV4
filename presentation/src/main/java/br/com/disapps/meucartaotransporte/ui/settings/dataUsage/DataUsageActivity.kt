package br.com.disapps.meucartaotransporte.ui.settings.dataUsage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.app.App
import br.com.disapps.meucartaotransporte.services.UpdateItinerariesService
import br.com.disapps.meucartaotransporte.services.UpdateLinesService
import br.com.disapps.meucartaotransporte.services.UpdateSchedulesService
import br.com.disapps.meucartaotransporte.services.UpdateShapesService
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_data_usage.*
import kotlinx.coroutines.experimental.async
import org.koin.android.architecture.ext.viewModel

class DataUsageActivity : BaseActivity(){

    override val viewModel by viewModel<DataUsageViewModel>()
    override val activityLayout = R.layout.activity_data_usage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init()

        update_line.setOnClickListener { async {  UpdateLinesService.startService(App.instance as Context, true) }}
        update_schedule.setOnClickListener { async {UpdateSchedulesService.startService(App.instance as Context, true)}}
        update_cwb_shapes.setOnClickListener { async {UpdateShapesService.startService(App.instance as Context, City.CWB, true)}}
        update_met_shapes.setOnClickListener { async {UpdateShapesService.startService(App.instance as Context, City.MET, true)}}
        update_cwb_itineraries.setOnClickListener { async {UpdateItinerariesService.startService(App.instance as Context, City.CWB, true)}}
        update_met_itineraries.setOnClickListener { async {UpdateItinerariesService.startService(App.instance as Context, City.MET, true)}}
    }

    companion object {
        fun launch(context: Context){
            context.startActivity(Intent(context, DataUsageActivity::class.java))
        }
    }
}