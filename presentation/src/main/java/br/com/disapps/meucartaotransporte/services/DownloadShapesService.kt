package br.com.disapps.meucartaotransporte.services

import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.data.BuildConfig
import br.com.disapps.data.api.CustomDownloadManager
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.PreferencesRepository
import br.com.disapps.meucartaotransporte.R
import org.koin.android.ext.android.inject

class DownloadShapesService : BaseService(){

    private val customDownloadManager :CustomDownloadManager by inject()
    private val preferences : PreferencesRepository by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){
            isRunning = true

            city = intent?.extras?.let{
                it.getSerializable(CITY)?.let {it as City}?: run {City.CWB}
            }?: run {
                City.CWB
            }

            downloadShapes(city)
        }else{

            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun downloadShapes(city: City){
        if(city == City.CWB){
            val idDownload = customDownloadManager.download(SHAPES_CWB, BuildConfig.DOWNLOAD_SHAPES, BuildConfig.DOWNLOAD_SHAPES_KEY, getString(R.string.downloading_shapes), city.toString())
            preferences.setIdDownloadShapesCwb(idDownload)
        }else {
            val idDownload = customDownloadManager.download(SHAPES_MET, BuildConfig.DOWNLOAD_SHAPES, BuildConfig.DOWNLOAD_SHAPES_KEY, getString(R.string.downloading_shapes), city.toString())
            preferences.setIdDownloadShapesMetropolitan(idDownload)
        }
    }

    companion object {

        private const val CITY = "city"
        private const val SHAPES_CWB = "shapesCWB.json"
        private const val SHAPES_MET = "shapesMET.json"

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