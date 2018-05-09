package br.com.disapps.meucartaotransporte.util

import android.app.Activity
import android.view.View
import android.widget.EditText
import br.com.disapps.meucartaotransporte.R
import com.appodeal.ads.Appodeal
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed
import com.appodeal.ads.native_ad.views.NativeAdViewAppWall



fun Activity.getEmptyView(message: String,viewGroup: View): View {
    val layout = this.inflateView(R.layout.empty_view,viewGroup )
    layout.findViewById<EditText>(R.id.empty_text).setText(message)
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