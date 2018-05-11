package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.interactor.itineraries.GetAllItinerariesJson
import br.com.disapps.domain.interactor.itineraries.SaveAllItinerariesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.android.ext.android.inject

class UpdateItinerariesService : BaseService(){

    private val getAllItinerariesJsonUseCase : GetAllItinerariesJson by inject()
    private val saveAllItinerariesJsonUseCase : SaveAllItinerariesJson by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning  = true

            intent?.extras?.getSerializable(CITY)?.let {
                city = it as City
            }?: run {
                city = City.CWB
            }

            isManual = intent?.extras?.getBoolean(IS_MANUAL)?:false

            if(isManual){
                showNotification(text = getString(R.string.updating_itineraries), infinityProgress = true )
                isComplete.observe(this, Observer {
                    if(it != null){
                        if(it){
                            showNotification(text = getString(R.string.update_itineraries_success))
                        }else{
                            showNotification(text = getString(R.string.update_itineraries_error))
                        }
                        stopSelf()
                    }
                })
            }

            updateItineraries(city)

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        getAllItinerariesJsonUseCase.dispose()
        saveAllItinerariesJsonUseCase.dispose()
    }

    private fun updateItineraries(city: City){
        getAllItinerariesJsonUseCase.execute(GetAllItinerariesJson.Params(city, updateProgressListener),
            onError ={
                isComplete.value = false
            },
            onSuccess = {
                saveItineraries(it, city)
            }
        )
    }

    private fun saveItineraries(json:String, city: City){

        showNotification(text = getString(R.string.saving_data), infinityProgress = true)

        saveAllItinerariesJsonUseCase.execute(SaveAllItinerariesJson.Params(json, city),
                onError = {
                    isComplete.value = false
                },
                onComplete= {
                    isComplete.value = true
                }
        )
    }

    private val updateProgressListener  = object :DownloadProgressListener{
        override fun onAttachmentDownloadUpdate(percent: Int) {
            showNotification(text = getString(R.string.updating_itineraries), progress =  percent)
        }
    }

    private fun showNotification(text:String, progress :Int = 0, infinityProgress: Boolean = false){
        if(city == City.CWB){
            showCustomNotification(context = this@UpdateItinerariesService,
                    channel = getUpdateDataNotification(UpdateData.CWB_ITINERARIES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.CWB_ITINERARIES).id,
                    text = text,
                    sortKey = "3",
                    progress = progress,
                    infinityProgress = infinityProgress)
        }else{
            showCustomNotification(context = this@UpdateItinerariesService,
                    channel = getUpdateDataNotification(UpdateData.MET_ITINERARIES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.MET_ITINERARIES).id,
                    text = text,
                    sortKey = "3",
                    progress = progress,
                    infinityProgress = infinityProgress)
        }
    }

    companion object {
        private const val IS_MANUAL = "manual"
        private const val CITY = "city"
        fun startService(context: Context, city: City,  manual :Boolean = true){
            context.startService(Intent(context, UpdateItinerariesService::class.java).apply {
                putExtra(IS_MANUAL, manual)
                putExtra(CITY, city)
            })
        }
    }
}