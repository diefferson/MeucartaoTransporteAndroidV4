package br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesWhite

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import br.com.disapps.domain.model.LineSchedule
import br.com.disapps.domain.model.Schedule

import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.widgets.busSchedules.BusSchedulesWidgetUtils

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
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            // BusSchedulesBlackWidgetConfigureActivity.deleteTitlePref(context, appWidgetId)
        }
    }

    companion object {

        private const val UPDATE_SCHEDULES = "br.com.disapps.meucartaotransporte.widgets.busSchedules.busSchedulesWhite.UPDATE_SCHEDULES"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val views = RemoteViews(context.packageName, R.layout.bus_schedules_white_widget)
            val list = ArrayList<Schedule>()
            list.add(Schedule("", true, "18:00"))
            list.add(Schedule("", true, "18:00"))
            list.add(Schedule("", true, "18:00"))
            list.add(Schedule("", true, "18:00"))

            val lineSchedule = LineSchedule("253", 1, "Teste Onibus", "11235", ArrayList(), list)

            BusSchedulesWidgetUtils.setupViews(views, context, lineSchedule)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

