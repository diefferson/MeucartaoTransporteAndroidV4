package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.interactor.itineraries.SaveAllItinerariesJson
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject


class UpdateItinerariesService : BaseService(){

    private val saveAllItinerariesJsonUseCase : SaveAllItinerariesJson by inject()
    private var city  = City.CWB
    private var listener : DownloadProgressListener? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning  = true

            intent?.extras?.let{

                listener = it.getSerializable(LISTENER) as DownloadProgressListener
                it.getSerializable(CITY)?.let {
                    city = it as City
                }?: run {
                    city = City.CWB
                }

                isManual = it.getBoolean(IS_MANUAL)

            }?: run {
                city = City.CWB
                isManual = false
            }

            if(isManual){
                showNotification(text = getString(R.string.updating_itineraries), infinityProgress = true)
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

            saveItineraries(city)

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        saveAllItinerariesJsonUseCase.dispose()
    }

    private fun saveItineraries( city: City){

        saveAllItinerariesJsonUseCase.execute(SaveAllItinerariesJson.Params(cacheDir.absolutePath+"/initenaries.json", city,updateProgressListener),
                onError = {
                    launch(UI) {
                        isComplete.value = false
                    }
                },
                onComplete= {
                    launch(UI) {
                        isComplete.value = true
                    }
                }
        )
    }

    private val updateProgressListener  = object :DownloadProgressListener{
        override fun onAttachmentDownloadUpdate(percent: Int) {
            listener?.onAttachmentDownloadUpdate(percent)
            showNotification(text = getString(R.string.updating_itineraries),
                    progress =  percent)
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
        private const val LISTENER = "listener"

        fun startService(context: Context, city: City,  manual :Boolean = true, listener: DownloadProgressListener? = null){
            try {
                context.startService(Intent(context, UpdateItinerariesService::class.java).apply {
                    putExtra(IS_MANUAL, manual)
                    putExtra(CITY, city)
                    putExtra(LISTENER, listener)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}