package br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesWhite

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesWidgetUtils
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesWidgetViewModel
import br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesWhite.BusSchedulesWhiteWidgetConfigureActivity.Companion.PREFS_NAME
import br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesWhite.BusSchedulesWhiteWidgetConfigureActivity.Companion.PREF_PREFIX_KEY

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [BusSchedulesWhiteWidgetConfigureActivity]
 */
class BusSchedulesWhiteWidget : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent?) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        if (intent != null) {
            if (intent.action == UPDATE_SCHEDULES) {
                val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    onUpdate(context, appWidgetManager, intArrayOf(appWidgetId))
                }
            }
        }
        super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            scheduleUpdate(context, appWidgetId)
        }
    }

    private fun scheduleUpdate(context: Context, appWidgetId: Int) {
        val intent = Intent(context, BusSchedulesWhiteWidget::class.java).apply {
            action = UPDATE_SCHEDULES
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        val pending = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pending)
        alarm.set(AlarmManager.RTC, System.currentTimeMillis()+(15*60*1000), pending)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            BusSchedulesWidgetViewModel.deleteData(context, appWidgetId, PREFS_NAME, PREF_PREFIX_KEY)
        }
    }

    companion object {

        private const val UPDATE_SCHEDULES = "br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesWhite.UPDATE_SCHEDULES"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val schedulesData = BusSchedulesWidgetViewModel.loadSchedulesData(context, appWidgetId,PREFS_NAME, PREF_PREFIX_KEY)

            val views = RemoteViews(context.packageName, R.layout.bus_schedules_white_widget)
            BusSchedulesWidgetUtils.setupViews(views, schedulesData, context)
            BusSchedulesWidgetUtils.setupRefreshIntent(views, context,BusSchedulesWhiteWidget::class.java, appWidgetId, UPDATE_SCHEDULES)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

