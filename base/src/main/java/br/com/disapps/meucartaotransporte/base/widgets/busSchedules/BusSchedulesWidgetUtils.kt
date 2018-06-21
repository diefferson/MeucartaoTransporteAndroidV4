package br.com.disapps.meucartaotransporte.base.widgets.busSchedules

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.util.getBusColorDrawable

object BusSchedulesWidgetUtils{

    fun setupViews(views: RemoteViews, schedulesData: BusSchedulesData, context: Context){

        val preferences = Preferences(context)
        if(preferences.getIsProSync()) {
            views.setViewVisibility(R.id.premium_view, View.GONE)
            views.setViewVisibility(R.id.content, View.VISIBLE)

            views.setTextViewText(R.id.line_code, schedulesData.codeLine)
            views.setTextViewText(R.id.line_name, schedulesData.nameLine)
            views.setTextViewText(R.id.bus_stop_name, schedulesData.nameStop)

            var hasSchedules = false

            if (schedulesData.nextSchedules.isNotEmpty()) {
                hasSchedules = true
                views.setViewVisibility(R.id.bus_schedule_one, View.VISIBLE)
                views.setTextViewText(R.id.bus_schedule_one, schedulesData.nextSchedules[0].time)
            } else {
                views.setViewVisibility(R.id.bus_schedule_one, View.GONE)
            }

            if (schedulesData.nextSchedules.size > 1) {
                views.setViewVisibility(R.id.bus_schedule_two, View.VISIBLE)
                views.setTextViewText(R.id.bus_schedule_two, schedulesData.nextSchedules[1].time)
            } else {
                views.setViewVisibility(R.id.bus_schedule_two, View.GONE)
            }

            if (schedulesData.nextSchedules.size > 2) {
                views.setViewVisibility(R.id.bus_schedule_three, View.VISIBLE)
                views.setTextViewText(R.id.bus_schedule_three, schedulesData.nextSchedules[2].time)
            } else {
                views.setViewVisibility(R.id.bus_schedule_three, View.GONE)
            }

            views.setInt(R.id.line_code, "setBackgroundResource", getBusColorDrawable(schedulesData.colorLine))

            if (!hasSchedules) {
                views.setViewVisibility(R.id.schedules, View.GONE)
                views.setViewVisibility(R.id.noSchedules, View.VISIBLE)
            }
        }else{
            views.setViewVisibility(R.id.premium_view, View.VISIBLE)
            views.setViewVisibility(R.id.content, View.INVISIBLE)
        }
    }

    fun setupRefreshIntent(views: RemoteViews, context: Context, clazz: Class<*>, appWidgetId: Int, action : String){
        val itUpdateCard = Intent(context, clazz)
        itUpdateCard.action = action
        itUpdateCard.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
        val piUpdateCard = PendingIntent.getBroadcast(context, appWidgetId, itUpdateCard, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setOnClickPendingIntent(R.id.refresh, piUpdateCard)
    }
}