package br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesBlack

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesWidgetUtils
import br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesBlack.BusSchedulesBlackWidgetConfigureActivity.Companion.PREFS_NAME
import br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesBlack.BusSchedulesBlackWidgetConfigureActivity.Companion.PREF_PREFIX_KEY
import android.app.AlarmManager
import android.app.PendingIntent
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesWidgetViewModel


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [BusSchedulesBlackWidgetConfigureActivity]
 */
class BusSchedulesBlackWidget : AppWidgetProvider() {

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
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            scheduleUpdate(context, appWidgetId)
        }
    }

    private fun scheduleUpdate(context: Context, appWidgetId: Int) {
        val intent = Intent(context, BusSchedulesBlackWidget::class.java).apply {
            action = UPDATE_SCHEDULES
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        val pending = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pending)
        alarm.set(AlarmManager.RTC, System.currentTimeMillis()+(15*60*1000), pending)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            BusSchedulesWidgetViewModel.deleteData(context, appWidgetId, PREFS_NAME, PREF_PREFIX_KEY)
        }
    }

    companion object {

        private const val UPDATE_SCHEDULES = "br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesBlack.UPDATE_SCHEDULES"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val schedulesData = BusSchedulesWidgetViewModel.loadSchedulesData(context, appWidgetId,PREFS_NAME, PREF_PREFIX_KEY)

            val views = RemoteViews(context.packageName, R.layout.bus_schedules_black_widget)
            BusSchedulesWidgetUtils.setupViews(views, schedulesData, context)
            BusSchedulesWidgetUtils.setupRefreshIntent(views, context, BusSchedulesBlackWidget::class.java, appWidgetId, UPDATE_SCHEDULES)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

