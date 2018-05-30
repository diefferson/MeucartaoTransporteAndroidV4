package br.com.disapps.meucartaotransporte.ui.custom

import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.v7.widget.CardView
import br.com.disapps.domain.model.Schedule
import com.appodeal.ads.NativeAd
import com.appodeal.ads.native_ad.views.NativeAdViewAppWall
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed
import com.chad.library.adapter.base.BaseViewHolder

fun BaseViewHolder.setSchedule(@IdRes viewId: Int, value: Schedule): BaseViewHolder{
    val view = getView<CustomSchedule>(viewId)
    view.setIsAdapt(value.adapt)
    view.setSchedule(value.time)
    return this
}

fun BaseViewHolder.setCardBackgroundColor(@IdRes viewId: Int, @ColorInt color : Int): BaseViewHolder{
    val view  = getView<CardView>(viewId)
    view.setCardBackgroundColor(color)
    return this
}

fun BaseViewHolder.setNativeAdFedd(@IdRes viewId: Int, nativeAd: NativeAd): BaseViewHolder{
    val view  = getView<NativeAdViewNewsFeed>(viewId)
    view.setNativeAd(nativeAd)
    return this
}

fun BaseViewHolder.setNativeAdContentStream(@IdRes viewId: Int, nativeAd: NativeAd): BaseViewHolder{
    val view  = getView<NativeAdViewContentStream>(viewId)
    view.setNativeAd(nativeAd)
    return this
}

fun BaseViewHolder.setNativeAdAppWall(@IdRes viewId: Int, nativeAd: NativeAd): BaseViewHolder{
    val view  = getView<NativeAdViewAppWall>(viewId)
    view.setNativeAd(nativeAd)
    return this
}

