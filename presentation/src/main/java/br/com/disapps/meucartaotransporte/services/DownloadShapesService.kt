package br.com.disapps.meucartaotransporte.services

import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.data.BuildConfig
import br.com.disapps.data.api.CustomDownloadManager
import br.com.disapps.data.utils.deleteFromCache
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.PreferencesRepository
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import org.koin.android.ext.android.inject

class DownloadShapesService : BaseService(){

    private val customDownloadManager :CustomDownloadManager by inject()
    private val preferences : PreferencesRepository by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!SaveShapesService.isRunning && !isRunning){
            isRunning = true
            city = intent?.extras?.let{
                it.getSerializable(CITY)?.let {it as City}?: run {City.CWB}
            }?: run {
                City.CWB
            }

            downloadShapes(city)

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_SHORT).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadShapes(city: City){
        val idDownload = customDownloadManager.download(FILE_NAME, BuildConfig.DOWNLOAD_SHAPES, BuildConfig.DOWNLOAD_SHAPES_KEY, getString(R.string.downloading_shapes), city.toString())
        if(idDownload > -1){
            if(city == City.CWB){
                preferences.setIdDownloadShapesCwb(idDownload)
            }else {
                preferences.setIdDownloadShapesMetropolitan(idDownload)
            }
        }else{
            deleteFromCache(SaveShapesService.FILE_PATH)
            showCustomNotification(this,
                    SaveShapesService.NOTIFICATION_CHANNEL,
                    SaveShapesService.NOTIFICATION_ID,
                    this.getString(R.string.download_shapes_error))
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
        private const val FILE_NAME = "shapes.json"

        fun startService(context: Context, city: City){
            try {
                context.startService(Intent(context, DownloadShapesService::class.java).apply {
                    putExtra(CITY, city)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}