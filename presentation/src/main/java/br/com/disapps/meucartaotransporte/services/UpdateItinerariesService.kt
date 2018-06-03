package br.com.disapps.meucartaotransporte.services

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import br.com.disapps.data.api.CustomDownloadManager
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.PreferencesRepository
import br.com.disapps.meucartaotransporte.R
import org.koin.android.ext.android.inject
import br.com.disapps.data.BuildConfig


class UpdateItinerariesService : BaseService(){

    private val customDownloadManager : CustomDownloadManager by inject()
    private val preferences : PreferencesRepository by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning  = true

            city = intent?.extras?.let{
                it.getSerializable(CITY)?.let {it as City}?: run {City.CWB}
            }?: run {
                City.CWB
            }

            downloadItineraries(city)

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadItineraries(city: City){
        if(city == City.CWB){
            val idDownload = customDownloadManager.download(ITINERARIES_CWB, BuildConfig.DOWNLOAD_ITINERARIES,BuildConfig.DOWNLOAD_ITINERARIES_KEY, getString(R.string.downloading_itineraries), city.toString())
            preferences.setIdDownloadItinerariesCwb(idDownload)
        }else {
            val idDownload = customDownloadManager.download(ITINERARIES_MET, BuildConfig.DOWNLOAD_ITINERARIES,BuildConfig.DOWNLOAD_ITINERARIES_KEY, getString(R.string.downloading_itineraries), city.toString())
            preferences.setIdDownloadItinerariesMetropolitan(idDownload)
        }
    }

    companion object {
        private const val CITY = "city"
        private const val ITINERARIES_CWB = "itinerariesCWB.json"
        private const val ITINERARIES_MET = "itinerariesMET.json"

        fun startService(context: Context, city: City){
            try {
                context.startService(Intent(context, UpdateItinerariesService::class.java).apply {
                    putExtra(CITY, city)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}