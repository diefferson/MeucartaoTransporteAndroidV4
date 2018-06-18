package br.com.disapps.meucartaotransporte.widgets.cardBalance

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.RemoteViews
import br.com.disapps.data.storage.preferences.Preferences
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.model.CardVO


object CardBalanceWidgetUtils{

    fun setupViews(views: RemoteViews, context: Context, card: CardVO){
        val preferences = Preferences(context)
        if(preferences.getIsProSync()){
            views.setViewVisibility(R.id.premium_view, View.GONE)
            views.setViewVisibility(R.id.content, View.VISIBLE)
            preferences.getIsProSync()
            views.setTextViewText(R.id.card_name, card.name)
            views.setTextViewText(R.id.card_balance, context.getString(R.string.card_balance_value, String.format("%.2f", card.balance)))
            views.setTextViewText(R.id.card_balance_date, card.balanceDate.subSequence(0,16))
            if(card.balance >15){
                views.setTextColor(R.id.card_balance, Color.GREEN)
            }else{
                views.setTextColor(R.id.card_balance, Color.RED)
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