package br.com.disapps.meucartaotransporte.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.model.City
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.util.cancelNotification
import br.com.disapps.meucartaotransporte.util.getUpdateDataNotification

class NotificationActionReceiver : BroadcastReceiver(){

    override fun onReceive(p0: Context, p1: Intent) {
        val action = p1.getStringExtra(ACTION)

        if (action == CANCEL_ACTION) {
            Toast.makeText(p0, "Cancel", Toast.LENGTH_SHORT).show()
            performCancel(p0, p1)
        } else if (action == RETRY_ACTION) {
            Toast.makeText(p0, "Cancel", Toast.LENGTH_SHORT).show()
            performRetry(p0, p1)

        }
        //This is used to close the notification tray
        val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        p0.sendBroadcast(it)
    }

    private fun performCancel(context: Context, intent: Intent) {

        val service = intent.getStringExtra(SERVICE)
        when(service){
            LINE_SERVICE -> {
                context.stopService(Intent(context, UpdateLinesService::class.java))
                cancelNotification(context, getUpdateDataNotification(UpdateData.LINES).id)
            }
            SCHEDULE_SERVICE ->{
                context.stopService(Intent(context, UpdateSchedulesService::class.java))
                cancelNotification(context, getUpdateDataNotification(UpdateData.SCHEDULES).id)
            }
            SHAPE_SERVICE -> {
                context.stopService(Intent(context, UpdateShapesService::class.java))
                if((intent.getSerializableExtra(CITY) as City) == City.CWB){
                    cancelNotification(context, getUpdateDataNotification(UpdateData.CWB_SHAPES).id)
                }else{
                    cancelNotification(context, getUpdateDataNotification(UpdateData.MET_SHAPES).id)
                }
            }
            ITINERARY_SERVICE ->  {
                context.stopService(Intent(context, UpdateItinerariesService::class.java))
                if((intent.getSerializableExtra(CITY) as City) == City.CWB){
                    cancelNotification(context, getUpdateDataNotification(UpdateData.CWB_ITINERARIES).id)
                }else{
                    cancelNotification(context, getUpdateDataNotification(UpdateData.MET_ITINERARIES).id)
                }
            }
        }
    }

    private fun performRetry(context: Context, intent: Intent) {val service = intent.getStringExtra(SERVICE)
        when(service){
            LINE_SERVICE -> UpdateLinesService.startService(context, true)
            SCHEDULE_SERVICE -> UpdateSchedulesService.startService(context, true)
            SHAPE_SERVICE -> UpdateShapesService.startService(context, intent.getSerializableExtra(CITY) as City, true)
            ITINERARY_SERVICE -> UpdateItinerariesService.startService(context, intent.getSerializableExtra(CITY) as City, true)
        }
    }

    companion object {
        const val ACTION = "action"
        const val SERVICE = "service"
        const val CANCEL_ACTION = "cancel"
        const val RETRY_ACTION = "retry"
        const val LINE_SERVICE = "lines"
        const val SCHEDULE_SERVICE = "schedules"
        const val ITINERARY_SERVICE = "itineraries"
        const val SHAPE_SERVICE = "shapes"
        const val CITY = "city"
    }
}