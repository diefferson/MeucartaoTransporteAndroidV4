package br.com.disapps.meucartaotransporte.base.ui.custom.animateCircleLoadingView.component

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.animation.DecelerateInterpolator
import br.com.disapps.meucartaotransporte.base.ui.custom.animateCircleLoadingView.animator.AnimationState

/**
 * @author jlmd
 */
class TopCircleBorderView(context: Context, parentWidth: Int, mainColor: Int, secondaryColor: Int) : ComponentViewAnimation(context, parentWidth, mainColor, secondaryColor) {
    private var paint: Paint? = null
    private var oval: RectF? = null
    private var arcAngle: Int = 0

    init {
        init()
    }

    private fun init() {
        initPaint()
        initOval()
        arcAngle = MIN_ANGLE
    }

    private fun initPaint() {
        paint = Paint()
        paint!!.color = mainColor
        paint!!.strokeWidth = strokeWidth.toFloat()
        paint!!.style = Paint.Style.STROKE
        paint!!.isAntiAlias = true
    }

    private fun initOval() {
        val padding = paint!!.strokeWidth / 2
        oval = RectF()
        oval!!.set(parentCenter - circleRadius, padding, parentCenter + circleRadius, circleRadius * 2)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawArcs(canvas)
    }

    private fun drawArcs(canvas: Canvas) {
        canvas.drawArc(oval!!, 270f, arcAngle.toFloat(), false, paint!!)
        canvas.drawArc(oval!!, 270f, (-arcAngle).toFloat(), false, paint!!)
    }

    fun startDrawCircleAnimation() {
        val valueAnimator = ValueAnimator.ofInt(MIN_ANGLE, MAX_ANGLE)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = 400
        valueAnimator.addUpdateListener { animation ->
            arcAngle = animation.animatedValue as Int
            invalidate()
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.MAIN_CIRCLE_DRAWN_TOP)
            }

            override fun onAnimationCancel(animation: Animator) {
                // Empty
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Empty
            }
        })
        valueAnimator.start()
    }

    companion object {

        private val MIN_ANGLE = 25
        private val MAX_ANGLE = 180
    }
}
