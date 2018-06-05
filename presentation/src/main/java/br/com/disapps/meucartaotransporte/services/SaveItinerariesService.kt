package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import br.com.disapps.domain.interactor.itineraries.SaveAllItinerariesJson
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class SaveItinerariesService : BaseService(){

    private val saveAllItinerariesJsonUseCase : SaveAllItinerariesJson by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){

            isRunning  = true

            city = intent?.extras?.let{
                it.getSerializable(CITY)?.let {it as City}?: run {City.CWB}
            }?: run {
                City.CWB
            }

            val id = if(city == City.CWB){
                getUpdateDataNotification(UpdateData.CWB_ITINERARIES).id
            }else{
                getUpdateDataNotification(UpdateData.MET_ITINERARIES).id
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForeground(id, NotificationCompat.Builder(this, CHANNEL)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText( getString(R.string.saving_data))
                        .setOnlyAlertOnce(true)
                        .setSmallIcon(R.drawable.bus)
                        .build())
            }else{
                showNotification(this@SaveItinerariesService,city,text =getString(R.string.saving_data), infinityProgress = true)
            }

            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        showNotification(this@SaveItinerariesService,city,  text = getString(R.string.update_itineraries_success))
                    }else{
                        showNotification(this@SaveItinerariesService,city,text = getString(R.string.update_itineraries_error))
                    }

                    stopService(Intent(this, DownloadItinerariesService::class.java))
                    stopSelf()
                }
            })

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
        saveAllItinerariesJsonUseCase.execute(SaveAllItinerariesJson.Params(city, if(city == City.CWB) FILE_PATH_CWB else FILE_PATH_MET),
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

    companion object {
        private const val CHANNEL = "UPDATING_DATA"
        private const val CITY = "city"
        private val BASE_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        val FILE_PATH_CWB = "$BASE_DIRECTORY/itinerariesCWB.json"
        val FILE_PATH_MET = "$BASE_DIRECTORY/itinerariesMET.json"

        fun startService(context: Context, city: City){
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(Intent(context,  SaveItinerariesService::class.java).apply {
                        putExtra(CITY, city)
                    })
                } else {
                    context.startService(Intent(context, SaveItinerariesService::class.java).apply {
                        putExtra(CITY, city)
                    })
                }
            }catch (e :Exception){
                e.stackTrace
            }
        }

        fun showNotification(context: Context,city:City, text:String, progress :Int = 0, infinityProgress: Boolean = false){
            if(city == City.CWB){
                showCustomNotification(context = context,
                        channel = getUpdateDataNotification(UpdateData.CWB_ITINERARIES).channel,
                        notificationId = getUpdateDataNotification(UpdateData.CWB_ITINERARIES).id,
                        text = text,
                        sortKey = "3",
                        progress = progress,
                        infinityProgress = infinityProgress)
            }else{
                showCustomNotification(context = context,
                        channel = getUpdateDataNotification(UpdateData.MET_ITINERARIES).channel,
                        notificationId = getUpdateDataNotification(UpdateData.MET_ITINERARIES).id,
                        text = text,
                        sortKey = "3",
                        progress = progress,
                        infinityProgress = infinityProgress)
            }
        }
    }
}