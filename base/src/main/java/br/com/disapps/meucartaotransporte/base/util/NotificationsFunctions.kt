package br.com.disapps.meucartaotransporte.base.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.model.NotificationChannels
import br.com.disapps.meucartaotransporte.base.model.UpdateData
import br.com.disapps.meucartaotransporte.base.model.UpdateDataNotification

/* Simple notification*/
fun showCustomNotification(context: Context, channel : String, notificationId : Int, text : String, infinityProgress: Boolean = false) {

    setupChannel(context,channel)

    val mBuilder = NotificationCompat.Builder(context, channel)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(text)
            .setOnlyAlertOnce(true)

    if(Build.VERSION.SDK_INT >= 21){
        mBuilder.setSmallIcon(R.drawable.bus)
    }else{
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
    }

    if(infinityProgress){
        mBuilder.setProgress(100, 100, true)
    }

    val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

    mNotificationManager?.notify(notificationId, mBuilder.build())
}

fun cancelNotification(context: Context,notificationId : Int){
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(notificationId)
}

fun getUpdateDataNotification( updateData: UpdateData) : UpdateDataNotification {
    return when(updateData){
        UpdateData.LINES -> UpdateDataNotification("UPDATING_DATA", 350)
        UpdateData.SCHEDULES -> UpdateDataNotification("UPDATING_DATA", 351)
        UpdateData.ITINERARIES -> UpdateDataNotification("UPDATING_DATA", 352)
        UpdateData.SHAPES -> UpdateDataNotification("UPDATING_DATA", 355)
    }
}

fun setupChannel(context: Context, channelId: String){

    val  notificationChannels = when(channelId){
        NotificationChannels.UPDATING_DATA.value -> NotificationChannels.UPDATING_DATA
        NotificationChannels.NEWS.value -> NotificationChannels.NEWS
        else -> NotificationChannels.DEFAULT
    }

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val importance = NotificationManager.IMPORTANCE_LOW
        val notificationChannel = NotificationChannel(notificationChannels.toString(), getChannelName(context, notificationChannels), importance)
        notificationChannel.apply {
            enableVibration(false)
            enableLights(false)
        }
        mNotificationManager?.createNotificationChannel(notificationChannel)
    }
}

private fun getChannelName(context: Context, notificationChannels: NotificationChannels) : String{
    return when(notificationChannels){
        NotificationChannels.UPDATING_DATA-> context.getString(R.string.channel_updating_data)
        NotificationChannels.NEWS -> context.getString(R.string.channel_news)
        NotificationChannels.DEFAULT -> context.getString(R.string.channel_default)
    }
}