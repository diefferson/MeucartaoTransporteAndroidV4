package br.com.disapps.meucartaotransporte.widgets.cardBalanceWhite

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.RemoteViews
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO
import kotlinx.coroutines.experimental.async


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [CardBalanceWidgetWhiteConfigureActivity]
 */
class CardBalanceWidgetWhite : AppWidgetProvider() {

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
            val views = RemoteViews(context.packageName, R.layout.card_balance_widget_white)
            views.setViewVisibility(R.id.loading_view, View.VISIBLE)
            appWidgetManager.updateAppWidget(appWidgetId, views)
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            CardBalanceWidgetWhiteConfigureActivity.deleteCodeCard(context, appWidgetId)
        }
    }

    companion object {

        private const val UPDATE_CARD = "br.com.disapps.meucartaotransporte.widgets.cardBalanceWhite.UPDATE_CARD"

        internal fun createAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = RemoteViews(context.packageName, R.layout.card_balance_widget_white)
            val card = CardBalanceWidgetWhiteConfigureActivity.loadCardInitialData(context, appWidgetId)
            card?.let {
                setupView(views,context,appWidgetManager,appWidgetId, card)
            }?: run{
                views.setViewVisibility(R.id.error_view, View.VISIBLE)
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

            val views = RemoteViews(context.packageName, R.layout.card_balance_widget_white)

            async {
                val card = CardBalanceWidgetWhiteConfigureActivity.loadCardData(context, appWidgetId)
                card?.let {
                    setupView(views,context,appWidgetManager,appWidgetId, card)
                }?: run{
                    views.setViewVisibility(R.id.error_view, View.VISIBLE)
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }
        }

        private fun setupView(views:RemoteViews, context:Context,appWidgetManager: AppWidgetManager, appWidgetId: Int, card:CardVO){
            views.setTextViewText(R.id.card_name, card.name)
            views.setTextViewText(R.id.card_balance, context.getString(R.string.card_balance_value, String.format("%.2f", card.balance)))
            views.setTextViewText(R.id.card_balance_date, card.balanceDate)
            if(card.balance >15){
                views.setTextColor(R.id.card_balance, Color.GREEN)
            }else{
                views.setTextColor(R.id.card_balance, Color.RED)
            }

            val itUpdateCard = Intent(context, CardBalanceWidgetWhite::class.java)
            itUpdateCard.action = UPDATE_CARD
            itUpdateCard.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId)
            val piUpdateCard = PendingIntent.getBroadcast(context, 0, itUpdateCard, 0)
            views.setOnClickPendingIntent(R.id.refresh, piUpdateCard)
            views.setViewVisibility(R.id.loading_view, View.GONE)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

