package br.com.disapps.meucartaotransporte.util

import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import br.com.disapps.meucartaotransporte.R

fun showNotification(context: Context,channel : String,  notificationId : Int, text : String) {

    val mBuilder = NotificationCompat.Builder(context, channel)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(text)
            .setSmallIcon(R.drawable.bus)

    val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    mNotificationManager?.notify(notificationId, mBuilder.build())
}