package br.com.disapps.meucartaotransporte.services

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import br.com.disapps.domain.interactor.shapes.SaveAllShapesJson
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification
import br.com.disapps.meucartaotransporte.util.showCustomNotification
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class SaveShapesService : BaseService(){

    private val saveAllShapesJsonUseCase : SaveAllShapesJson by inject()
    private var city  = City.CWB

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if(!isRunning){

            isRunning = true

            city = intent?.extras?.let{
                it.getSerializable(CITY)?.let {it as City}?: run {City.CWB}
            }?: run {
                City.CWB
            }

            isComplete.observe(this, Observer {
                if(it != null){
                    if(it){
                        showNotification(text =  getString(R.string.update_shapes_success))
                    }else{
                        showNotification(text =  getString(R.string.update_shapes_error))
                    }

                    stopService(Intent(this, UpdateShapesService::class.java))
                    stopSelf()
                }
            })

            saveShapes(city)

        }else{
            Toast.makeText(this, getString(R.string.wait_for_actual_proccess), Toast.LENGTH_LONG).show()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        saveAllShapesJsonUseCase.dispose()
    }

    private fun saveShapes(city: City){
        showNotification(text =  getString(R.string.saving_data), infinityProgress = true)
        saveAllShapesJsonUseCase.execute(SaveAllShapesJson.Params(city, if(city == City.CWB)  FILE_PATH_CWB  else FILE_PATH_MET),
            onError = {
                launch(UI) {
                    isComplete.value = false
                }
            },

            onComplete = {
                launch(UI) {
                    isComplete.value = true
                }
            }
        )
    }

    private fun showNotification(text:String,progress :Int = 0, infinityProgress: Boolean = false){
        if(city == City.CWB){
           showCustomNotification(context = this@SaveShapesService,
                    channel = getUpdateDataNotification(UpdateData.CWB_SHAPES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.CWB_SHAPES).id,
                    text = text,
                    sortKey = "4",
                    progress = progress,
                    infinityProgress = infinityProgress)
        }else{
            showCustomNotification(context = this@SaveShapesService,
                    channel = getUpdateDataNotification(UpdateData.MET_SHAPES).channel,
                    notificationId = getUpdateDataNotification(UpdateData.MET_SHAPES).id,
                    text = text,
                    sortKey = "4",
                    progress = progress,
                    infinityProgress = infinityProgress)
        }
    }

    companion object {
        private const val CITY = "city"
        private val BASE_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        private val FILE_PATH_CWB = "$BASE_DIRECTORY/shapesCWB.json"
        private val FILE_PATH_MET = "$BASE_DIRECTORY/shapesMET.json"

        fun startService(context: Context, city: City){
            try {
                context.startService(Intent(context, SaveShapesService::class.java).apply {
                    putExtra(CITY, city)
                })
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }
}