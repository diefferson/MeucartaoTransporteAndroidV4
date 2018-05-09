package br.com.disapps.meucartaotransporte.util

import android.app.Activity
import android.view.View
import android.widget.EditText
import br.com.disapps.meucartaotransporte.R
import com.appodeal.ads.Appodeal
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed

fun Activity.getEmptyView(message: String,viewGroup: View): View {
    val layout = this.inflateView(R.layout.empty_view,viewGroup )
    layout.findViewById<EditText>(R.id.empty_text).setText(message)
    return layout
}

fun Activity.getAdViewContentStream(viewGroup: View): View {
    Appodeal.cache(this, Appodeal.NATIVE)
    val adView = this.inflateView(R.layout.item_ads_large, viewGroup) as NativeAdViewContentStream
    val nativeAd = Appodeal.getNativeAds(1)
    if(nativeAd.size >0){
        adView.setNativeAd(nativeAd[0])
    }
    Appodeal.cache(this, Appodeal.NATIVE)
    return adView
}

fun Activity.getAdViewNewsFeed(viewGroup: View): View {
    Appodeal.cache(this, Appodeal.NATIVE)
    val adView = this.inflateView(R.layout.item_ads, viewGroup) as NativeAdViewNewsFeed
    val nativeAd = Appodeal.getNativeAds(1)
    if(nativeAd.size >0){
        adView.setNativeAd(nativeAd[0])
    }
    Appodeal.cache(this, Appodeal.NATIVE)
    return adView
}