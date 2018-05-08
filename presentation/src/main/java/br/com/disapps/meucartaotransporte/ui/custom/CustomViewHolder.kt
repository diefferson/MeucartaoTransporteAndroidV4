package br.com.disapps.meucartaotransporte.ui.custom

import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.v7.widget.CardView
import android.view.View
import br.com.disapps.domain.model.Schedule
import com.appodeal.ads.NativeAd
import com.appodeal.ads.native_ad.views.NativeAdViewAppWall
import com.appodeal.ads.native_ad.views.NativeAdViewContentStream
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed
import com.chad.library.adapter.base.BaseViewHolder

class CustomViewHolder(view: View) : BaseViewHolder(view){

    fun setSchedule(@IdRes viewId: Int, value: Schedule): CustomViewHolder {
        val view = getView<CustomSchedule>(viewId)
        view.setIsAdapt(value.adapt)
        view.setSchedule(value.time)
        return this
    }

    fun setCardBackgroundColor(@IdRes viewId: Int, @ColorInt color : Int): CustomViewHolder{
        val view  = getView<CardView>(viewId)
        view.setCardBackgroundColor(color)
        return this
    }

    fun setNativeListAd(@IdRes viewId: Int, nativeAd: NativeAd): CustomViewHolder{
        val view  = getView<CardView>(viewId) as NativeAdViewNewsFeed
        view.setNativeAd(nativeAd)
        return this
    }

}