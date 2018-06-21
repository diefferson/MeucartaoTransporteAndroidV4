package br.com.disapps.meucartaotransporte.base.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import br.com.disapps.meucartaotransporte.base.model.LineVO

class ActivityLaunchHelper {

    companion object {

        const val EXTRA_EDIT = "EDIT"

        private const val URL_BASE = "https://meucartaotransporte.com.br"
        private const val URL_CARDS = "$URL_BASE/cartoes"
        private const val URL_LINES = "$URL_BASE/linhas"
        private const val URL_LINE = "$URL_BASE/linha/"

        fun launchCards(activity: Activity, options: ActivityOptionsCompat? = null) {
            val starter = cardsIntent(activity)
            if (options == null) {
                activity.startActivity(starter)
            } else {
                ActivityCompat.startActivity(activity, starter, options.toBundle())
            }
        }

        fun launchLines(activity: Activity) {
            ActivityCompat.startActivity(activity, linesIntent(activity),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle())
        }

        fun launchLine(activity: Activity){

        }

        fun linesIntent(context: Context) = baseIntent(URL_LINES, context)

        fun cardsIntent(context: Context): Intent = baseIntent(URL_CARDS, context)

        fun lineIntent(context: Context, line:LineVO) :Intent{
            return baseIntent("$URL_LINES${line.code}", context)
        }

        private fun baseIntent(url: String, context: Context? = null): Intent {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    .addCategory(Intent.CATEGORY_DEFAULT)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
            if (context != null) {
                intent.`package` = context.packageName
            }
            return intent
        }
    }
}