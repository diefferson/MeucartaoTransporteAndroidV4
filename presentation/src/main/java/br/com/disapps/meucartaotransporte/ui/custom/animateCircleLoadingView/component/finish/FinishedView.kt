package br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.component.finish

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.LightingColorFilter
import android.graphics.Paint
import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.animator.AnimationState
import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.component.ComponentViewAnimation

/**
 * @author jlmd
 */
abstract class FinishedView(context: Context, parentWidth: Int, mainColor: Int, secondaryColor: Int,
                            protected val tintColor: Int) : ComponentViewAnimation(context, parentWidth, mainColor, secondaryColor) {
    private var maxImageSize: Int = 0
    private var circleMaxRadius: Int = 0
    private var originalFinishedBitmap: Bitmap? = null
    private var currentCircleRadius: Float = 0.toFloat()
    private var imageSize: Int = 0

    protected abstract val drawable: Int

    protected abstract val drawableTintColor: Int

    protected abstract val circleColor: Int

    init {
        init()
    }

    private fun init() {
        maxImageSize = 140 * parentWidth / 700
        circleMaxRadius = 140 * parentWidth / 700
        currentCircleRadius = circleRadius
        imageSize = MIN_IMAGE_SIZE
        originalFinishedBitmap = BitmapFactory.decodeResource(resources, drawable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircle(canvas)
        drawCheckedMark(canvas)
    }

    private fun drawCheckedMark(canvas: Canvas) {
        val paint = Paint()
        paint.colorFilter = LightingColorFilter(drawableTintColor, 0)

        val bitmap = Bitmap.createScaledBitmap(originalFinishedBitmap!!, imageSize, imageSize, true)
        canvas.drawBitmap(bitmap, parentCenter - bitmap.width / 2,
                parentCenter - bitmap.height / 2, paint)
    }

    fun drawCircle(canvas: Canvas) {
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = circleColor
        paint.isAntiAlias = true
        canvas.drawCircle(parentCenter, parentCenter, currentCircleRadius, paint)
    }

    fun startScaleAnimation() {
        startScaleCircleAnimation()
        startScaleImageAnimation()
    }

    private fun startScaleCircleAnimation() {
        val valueCircleAnimator = ValueAnimator.ofFloat(circleRadius + strokeWidth / 2, circleMaxRadius.toFloat())
        valueCircleAnimator.duration = 1000
        valueCircleAnimator.addUpdateListener { animation ->
            currentCircleRadius = animation.animatedValue as Float
            invalidate()
        }
        valueCircleAnimator.start()
    }

    private fun startScaleImageAnimation() {
        val valueImageAnimator = ValueAnimator.ofInt(MIN_IMAGE_SIZE, maxImageSize)
        valueImageAnimator.duration = 1000
        valueImageAnimator.addUpdateListener { animation ->
            imageSize = animation.animatedValue as Int
            invalidate()
        }
        valueImageAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Empty
            }

            override fun onAnimationEnd(animation: Animator) {
                setState(AnimationState.ANIMATION_END)
            }

            override fun onAnimationCancel(animation: Animator) {
                // Empty
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Empty
            }
        })
        valueImageAnimator.start()
    }

    companion object {

        private val MIN_IMAGE_SIZE = 1
    }
}
