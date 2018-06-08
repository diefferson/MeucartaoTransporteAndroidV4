package br.com.disapps.meucartaotransporte.widgets.busSchedules

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.meucartaotransporte.R

object BusSchedulesWidgetUtils{
    fun setupViews(views: RemoteViews, context: Context, lineSchedule: LineSchedule){

        views.setTextViewText(R.id.line_code, lineSchedule.lineCode)
        views.setTextViewText(R.id.line_name, "teste")
        views.setTextViewText(R.id.bus_stop_name, lineSchedule.busStopName)

        var hasSchedules = false;

        if(lineSchedule.nextSchedules.isNotEmpty()){
            hasSchedules = true
            views.setViewVisibility(R.id.bus_schedule_one, View.VISIBLE)
            views.setTextViewText(R.id.bus_schedule_one, lineSchedule.nextSchedules[0].time)
        }else{
            views.setViewVisibility(R.id.bus_schedule_one, View.GONE)
        }

        if(lineSchedule.nextSchedules.size > 1){
            views.setViewVisibility(R.id.bus_schedule_two, View.VISIBLE)
            views.setTextViewText(R.id.bus_schedule_two, lineSchedule.nextSchedules[1].time)
        }else{
            views.setViewVisibility(R.id.bus_schedule_one, View.GONE)
        }

        if(lineSchedule.nextSchedules.size > 2){
            views.setViewVisibility(R.id.bus_schedule_three, View.VISIBLE)
            views.setTextViewText(R.id.bus_schedule_three, lineSchedule.nextSchedules[2].time)
        }else {
            views.setViewVisibility(R.id.bus_schedule_one, View.GONE)
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