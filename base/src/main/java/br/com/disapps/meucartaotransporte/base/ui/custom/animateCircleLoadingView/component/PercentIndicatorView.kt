package br.com.disapps.meucartaotransporte.base.ui.custom.animateCircleLoadingView.component

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.animation.AlphaAnimation
import android.support.v7.widget.AppCompatTextView

/**
 * @author jlmd
 */
class PercentIndicatorView(context: Context, private val parentWidth: Int, private val textColor: Int = Color.WHITE) : AppCompatTextView(context) {

    init {
        init()
    }

    private fun init() {
        val textSize = 18
        setTextSize(textSize.toFloat())
        setTextColor(this.textColor)
        gravity = Gravity.CENTER
        alpha = 0.8f
    }

    fun setPercent(percent: Int) {
        text = percent.toString() + "%"
    }

    fun startAlphaAnimation() {
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = 700
        alphaAnimation.fillAfter = true
        startAnimation(alphaAnimation)
    }
}
