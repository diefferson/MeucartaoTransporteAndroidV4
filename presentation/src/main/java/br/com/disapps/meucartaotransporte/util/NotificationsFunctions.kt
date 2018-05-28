package br.com.disapps.meucartaotransporte.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.NotificationChannels
import br.com.disapps.meucartaotransporte.model.UpdateData
import br.com.disapps.meucartaotransporte.model.UpdateDataNotification

/* Simple notification*/
fun showCustomNotification(context: Context, channel : String, notificationId : Int, text : String,sortKey :String = "", progress: Int = 0, infinityProgress: Boolean = false) {

    setupChannel(context,channel)

    val mBuilder = NotificationCompat.Builder(context, channel)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(text)
            .setSmallIcon(R.drawable.bus)
            .setOnlyAlertOnce(true)

    if(sortKey != ""){
        mBuilder.setSortKey(sortKey)
    }

    if(progress >0){
        mBuilder.setProgress(100, progress,false)
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
        UpdateData.CWB_ITINERARIES -> UpdateDataNotification("UPDATING_DATA", 352)
        UpdateData.MET_ITINERARIES -> UpdateDataNotification("UPDATING_DATA", 353)
        UpdateData.CWB_SHAPES -> UpdateDataNotification("UPDATING_DATA", 354)
        UpdateData.MET_SHAPES -> UpdateDataNotification("UPDATING_DATA", 355)
    }
}

private fun setupChannel(context: Context, channelId: String){

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