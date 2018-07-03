package br.com.disapps.meucartaotransporte.ui.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat

class NavigationHelper {

    companion object {

        private const val URL_BASE = "https://diefferson.com.br"
        private const val URL_FEATURE_ONE = "$URL_BASE/featureOne"
        private const val URL_FEATURE_TWO = "$URL_BASE/featureTwo"

        fun launchFeatureOne(activity: Activity) {
            ActivityCompat.startActivity(activity,
                    baseIntent(URL_FEATURE_ONE, activity),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle())
        }

        fun launchFeatureTwo(activity: Activity) {
            ActivityCompat.startActivity(activity,
                    baseIntent(URL_FEATURE_TWO, activity),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle())
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