package br.com.disapps.meucartaotransporte.util

import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.model.UpdateDataNotification

/* Simple notification*/
fun showCustomNotification(context: Context, channel : String, notificationId : Int, text : String,sortKey :String = "",  progress: Int = 0, infinityProgress: Boolean = false) {

    val mBuilder = NotificationCompat.Builder(context, channel)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(text)
            .setSmallIcon(R.drawable.bus)

    if(sortKey != ""){
        mBuilder.setSortKey(sortKey)
    }

    if(progress >0){
        mBuilder.setProgress(100, progress,false)
        mBuilder.setOngoing(true)
    }

    if(infinityProgress){
        mBuilder.setProgress(100, 100, true)
        mBuilder.setOngoing(true)
    }

    val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    mNotificationManager?.notify(notificationId, mBuilder.build())
}

fun getUpdateDataNotification( updateData: UpdateData) : UpdateDataNotification {
    return when(updateData){
        UpdateData.LINES -> UpdateDataNotification("UPDATING_DATA", 350)
        UpdateData.SCHEDULES -> UpdateDataNotification("UPDATING_DATA", 351)
        UpdateData.CWB_ITINERARIES -> UpdateDataNotification("UPDATING_DATA", 352)
        UpdateData.MET_ITINERARIES -> UpdateDataNotification("UPDATING_DATA", 353)
        UpdateData.CWB_SHAPES -> UpdateDataNotification("UPDATING_DATA", 354)
        UpdateData.MET_SHAPES -> UpdateDataNotification("UPDATING_DATA", 355)
    }
}