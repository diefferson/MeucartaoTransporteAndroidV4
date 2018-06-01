package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Environment
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

            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        showNotification(text = getString(R.string.update_itineraries_success))
                    }else{
                        showNotification(text = getString(R.string.update_itineraries_error))
                    }

                    stopService(Intent(this, UpdateItinerariesService::class.java))
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
        showNotification(text =  getString(R.string.saving_data), infinityProgress = true)
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

    private fun showNotification(text:String, progress :Int = 0, infinityProgress: Boolean = false){
        if(city == City.CWB){
            showCustomNotification(context = this@SaveItinerariesService,
                    channel = getUpdateDataNotification(UpdateData.CWB_ITINERARIES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.CWB_ITINERARIES).id,
                    text = text,
                    sortKey = "3",
                    progress = progress,
                    infinityProgress = infinityProgress)
        }else{
            showCustomNotification(context = this@SaveItinerariesService,
                    channel = getUpdateDataNotification(UpdateData.MET_ITINERARIES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.MET_ITINERARIES).id,
                    text = text,
                    sortKey = "3",
                    progress = progress,
                    infinityProgress = infinityProgress)
        }
    }

    companion object {
        private const val CITY = "city"
        private val BASE_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        private val FILE_PATH_CWB = "$BASE_DIRECTORY/itinerariesCWB.json"
        private val FILE_PATH_MET = "$BASE_DIRECTORY/itinerariesMET.json"

        fun startService(context: Context, city: City){
            try {
                context.startService(Intent(context, SaveItinerariesService::class.java).apply {
                    putExtra(CITY, city)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}