package br.com.disapps.meucartaotransporte.base.widgets.cardBalance.cardBalanceBlack

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.base.widgets.cardBalance.CardBalanceWidgetUtils
import br.com.disapps.meucartaotransporte.base.widgets.cardBalance.CardBalanceWidgetViewModel
import br.com.disapps.meucartaotransporte.base.widgets.cardBalance.cardBalanceBlack.CardBalanceWidgetBlackConfigureActivity.Companion.PREFS_NAME
import br.com.disapps.meucartaotransporte.base.widgets.cardBalance.cardBalanceBlack.CardBalanceWidgetBlackConfigureActivity.Companion.PREF_PREFIX_KEY
import kotlinx.coroutines.experimental.async


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [CardBalanceWidgetBlackConfigureActivity]
 */
class CardBalanceWidgetBlack : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent?) {
        val appWidgetManager = AppWidgetManager.getInstance(context)

        if (intent != null) {
            if (intent.action == UPDATE_CARD) {
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
            val views = RemoteViews(context.packageName, R.layout.card_balance_widget_black)
            views.setViewVisibility(R.id.loading_view, View.VISIBLE)
            views.setViewVisibility(R.id.error_view, View.GONE)
            appWidgetManager.updateAppWidget(appWidgetId, views)
            updateAppWidget(context, appWidgetManager, appWidgetId)
            scheduleUpdate(context, appWidgetId)
        }
    }

    private fun scheduleUpdate(context: Context, appWidgetId: Int) {
        val intent = Intent(context, CardBalanceWidgetBlack::class.java).apply {
            action = UPDATE_CARD
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        val pending = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pending)
        alarm.set(AlarmManager.RTC, System.currentTimeMillis()+(4*60*60*1000), pending)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            CardBalanceWidgetViewModel.deleteCodeCard(context, appWidgetId, PREFS_NAME, PREF_PREFIX_KEY)
        }
    }

    companion object {

        private const val UPDATE_CARD = "br.com.disapps.meucartaotransporte.base.widgets.cardBalance.cardBalanceBlack.UPDATE_CARD"

        internal fun createAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val views = RemoteViews(context.packageName, R.layout.card_balance_widget_black)

            val card = CardBalanceWidgetViewModel.loadCardInitialData(context, appWidgetId,PREFS_NAME, PREF_PREFIX_KEY)
            if(card!= null){
                CardBalanceWidgetUtils.setupViews(views,context, card)
            }else{
                views.setViewVisibility(R.id.error_view, View.VISIBLE)
            }

            CardBalanceWidgetUtils.setupRefreshIntent(views, context, CardBalanceWidgetBlack::class.java, appWidgetId, UPDATE_CARD)
            views.setViewVisibility(R.id.loading_view, View.GONE)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val views = RemoteViews(context.packageName, R.layout.card_balance_widget_black)

            async {
                val card = CardBalanceWidgetViewModel.loadCardData(context, appWidgetId, PREFS_NAME, PREF_PREFIX_KEY)
                if(card != null){
                    CardBalanceWidgetUtils.setupViews(views,context, card)
                }else{
                    views.setViewVisibility(R.id.error_view, View.VISIBLE)
                }

                CardBalanceWidgetUtils.setupRefreshIntent(views, context, CardBalanceWidgetBlack::class.java, appWidgetId, UPDATE_CARD)
                views.setViewVisibility(R.id.loading_view, View.GONE)
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}

