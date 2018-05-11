package br.com.disapps.meucartaotransporte.util

import android.animation.ValueAnimator
import android.app.Activity
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.exception.UiError
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.appodeal.ads.Appodeal
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed
import com.appodeal.ads.native_ad.views.NativeAdViewAppWall

fun Activity.getErrorView(uiError: UiError,viewGroup: View) :View{
    return when(uiError.knownError){

        KnownError.LINK_DOCUMENT_CARD_EXCEPTION -> {
            inflateView(R.layout.error_expected, viewGroup).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.alert)
                findViewById<TextView>(R.id.description).text = getString(R.string.card_link_message)
                findViewById<Button>(R.id.known_more).setOnClickListener { getCustomChromeTabs().launchUrl(this@getErrorView, Uri.parse(resources.getString(R.string.url_balance_card))) }
            }
        }

        KnownError.INACTIVE_CARD_EXCEPTION ->{
            inflateView(R.layout.error_expected,viewGroup ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.vish)
                findViewById<TextView>(R.id.description).text = getString(R.string.inactive_card)
                findViewById<Button>(R.id.known_more).visibility = View.INVISIBLE
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.vish)
            }
        }

        KnownError.INVALID_CARD_EXCEPTION -> {
            inflateView(R.layout.error_expected,viewGroup ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.vish)
                findViewById<TextView>(R.id.description).text = getString(R.string.invalid_card)
                findViewById<Button>(R.id.known_more).visibility = View.INVISIBLE
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.vish)
            }
        }

        KnownError.INVALID_LINE_EXCEPTION-> {
            inflateView(R.layout.error_expected,viewGroup ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.vish)
                findViewById<TextView>(R.id.description).text = getString(R.string.invalid_line)
                findViewById<Button>(R.id.known_more).visibility = View.INVISIBLE
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.vish)
            }
        }

        KnownError.INVALID_DOCUMENT_EXCEPTION-> {
            inflateView(R.layout.error_expected,viewGroup ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.vish)
                findViewById<TextView>(R.id.description).text = getString(R.string.invalid_cpf)
                findViewById<Button>(R.id.known_more).visibility = View.INVISIBLE
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.vish)
            }
        }

        KnownError.URBS_RETURN_ERROR->{
            inflateView(R.layout.error_expected,viewGroup ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.not_fault)
                findViewById<TextView>(R.id.description).text = uiError.message
                findViewById<Button>(R.id.known_more).visibility = View.INVISIBLE
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.not_fault)
            }
        }

        else -> {
            inflateView(R.layout.error_expected,viewGroup ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.gave_bad)
                findViewById<TextView>(R.id.description).text = getString(R.string.network_generic_error)
                findViewById<Button>(R.id.known_more).text = getString(R.string.tray_again)
                findViewById<Button>(R.id.known_more).setOnClickListener { recreate() }
            }
        }
    }

}

fun Activity.getEmptyView(message: String,viewGroup: View): View {
    val layout = this.inflateView(R.layout.empty_view,viewGroup )
    layout.findViewById<TextView>(R.id.empty_text).text = message
    return layout
}

fun Activity.getAdViewContentStream(): View {
    Appodeal.cache(this, Appodeal.NATIVE)
    val nativeAdView = NativeAdViewContentStream(this)
    val nativeAd = Appodeal.getNativeAds(1)
    if(nativeAd.size >0){
         nativeAdView.setNativeAd(nativeAd[0])
    }
    Appodeal.cache(this, Appodeal.NATIVE)
    return nativeAdView
}

fun Activity.getAdViewNewsFeed(): View {
    Appodeal.cache(this, Appodeal.NATIVE)
    val adView = NativeAdViewNewsFeed(this)
    val nativeAd = Appodeal.getNativeAds(1)
    if(nativeAd.size >0){
        adView.setNativeAd(nativeAd[0])
    }
    Appodeal.cache(this, Appodeal.NATIVE)
    return adView
}