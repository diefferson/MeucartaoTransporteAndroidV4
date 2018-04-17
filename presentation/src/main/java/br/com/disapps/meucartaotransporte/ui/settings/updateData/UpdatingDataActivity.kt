package br.com.disapps.meucartaotransporte.ui.settings.updateData

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.EventStatus
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.services.UpdateItinerariesService
import br.com.disapps.meucartaotransporte.services.UpdateLinesService
import br.com.disapps.meucartaotransporte.services.UpdateSchedulesService
import br.com.disapps.meucartaotransporte.services.UpdateShapesService
import br.com.disapps.meucartaotransporte.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_updating_data.*
import org.koin.android.architecture.ext.getViewModel
import org.koin.android.architecture.ext.viewModel

class UpdatingDataActivity : BaseActivity(){

    override val viewModel  : UpdatingDataViewModel
            get() = getViewModel()

    override val activityLayout = R.layout.activity_updating_data
    private var updateData = UpdateData.LINES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        back_button.setOnClickListener { finish() }

        if(!viewModel.isRequested){
            setupUpdate()
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.events.observe(this, Observer {
            it?.let {
                loading_message.text = it.message
                if(it.status != EventStatus.START ){
                    back_button.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupUpdate(){
        viewModel.getUpdateDataEvent()
        updateData = intent.extras.getSerializable(UPDATE) as UpdateData
        when(updateData){
            UpdateData.LINES -> UpdateLinesService.startService(this)
            UpdateData.SCHEDULES -> UpdateSchedulesService.startService(this)
            UpdateData.CWB_ITINERARIES -> UpdateItinerariesService.startService(this, City.CWB)
            UpdateData.MET_ITINERARIES -> UpdateItinerariesService.startService(this, City.MET)
            UpdateData.CWB_SHAPES -> UpdateShapesService.startService(this, City.CWB)
            UpdateData.MET_SHAPES -> UpdateShapesService.startService(this, City.MET)
        }
    }

    companion object {
        private const val UPDATE = "updateData"
        fun launch(context: Context, updateData :UpdateData){
            context.startActivity(Intent(context, UpdatingDataActivity::class.java).apply {
                putExtra(UPDATE, updateData)
            })
        }
    }
}