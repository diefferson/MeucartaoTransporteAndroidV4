package br.com.disapps.meucartaotransporte.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import br.com.disapps.domain.model.City

class NotificationActionReceiver : BroadcastReceiver(){

    override fun onReceive(p0: Context, p1: Intent) {
        val action = p1.getStringExtra(ACTION)

        if (action == CANCEL_ACTION) {
            performCancel(p0, p1)
        } else if (action == RETRY_ACTION) {
            performRetry(p0, p1)

        }
        //This is used to close the notification tray
        val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        p0.sendBroadcast(it)
    }

    private fun performCancel(context: Context, intent: Intent) {
        val service = intent.getStringExtra(SERVICE)
        when(service){
            LINE_SERVICE -> context.stopService(Intent(context, UpdateLinesService::class.java))
            SCHEDULE_SERVICE -> context.stopService(Intent(context, UpdateSchedulesService::class.java))
            SHAPE_SERVICE -> context.stopService(Intent(context, UpdateShapesService::class.java))
            ITINERARY_SERVICE -> context.stopService(Intent(context, UpdateItinerariesService::class.java))
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