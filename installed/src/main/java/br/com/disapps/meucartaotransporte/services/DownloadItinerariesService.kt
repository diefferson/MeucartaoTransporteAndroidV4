package br.com.disapps.meucartaotransporte.services

import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.data.api.CustomDownloadManager
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.PreferencesRepository
import br.com.disapps.meucartaotransporte.R
import org.koin.android.ext.android.inject
import br.com.disapps.data.BuildConfig
import br.com.disapps.data.utils.deleteFromCache
import br.com.disapps.meucartaotransporte.base.util.showCustomNotification


class DownloadItinerariesService : BaseService(){

    private val customDownloadManager : CustomDownloadManager by inject()
    private val preferences : PreferencesRepository by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!SaveItinerariesService.isRunning && !isRunning){
            isRunning = true
            city = intent?.extras?.let{
                it.getSerializable(CITY)?.let {it as City}?: run {City.CWB}
            }?: run {
                City.CWB
            }

            downloadItineraries(city)

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_SHORT).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadItineraries(city: City){
        val idDownload = customDownloadManager.download(FILE_NAME, BuildConfig.DOWNLOAD_ITINERARIES,BuildConfig.DOWNLOAD_ITINERARIES_KEY, getString(R.string.downloading_itineraries), city.toString())
        if(idDownload > -1){
            if(city == City.CWB){
                preferences.setIdDownloadItinerariesCwb(idDownload)
            }else {
                preferences.setIdDownloadItinerariesMetropolitan(idDownload)
            }
        }else{
            deleteFromCache(SaveItinerariesService.FILE_PATH)
            showCustomNotification(this,
                    SaveItinerariesService.NOTIFICATION_CHANNEL,
                    SaveItinerariesService.NOTIFICATION_ID,
                    this.getString(R.string.download_itineraries_error))
            this.stopSelf()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    companion object {
        var isRunning = false
        private const val CITY = "city"
        private const val FILE_NAME = "itineraries.json"

        fun startService(context: Context, city: City){
            try {
                context.startService(Intent(context, DownloadItinerariesService::class.java).apply {
                    putExtra(CITY, city)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}