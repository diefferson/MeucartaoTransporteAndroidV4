package br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.animator

import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.AnimatedCircleLoadingView
import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.component.*
import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.component.finish.FinishedFailureView
import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.component.finish.FinishedOkView

/**
 * @author jlmd
 */
class ViewAnimator : ComponentViewAnimation.StateListener {

    private var initialCenterCircleView: InitialCenterCircleView? = null
    private var rightCircleView: RightCircleView? = null
    private var sideArcsView: SideArcsView? = null
    private var topCircleBorderView: TopCircleBorderView? = null
    private var mainCircleView: MainCircleView? = null
    private var finishedOkView: FinishedOkView? = null
    private var finishedFailureView: FinishedFailureView? = null
    private var percentIndicatorView: PercentIndicatorView? = null
    private var finishedState: AnimationState? = null
    private var resetAnimator: Boolean = false
    private var animationListener: AnimatedCircleLoadingView.AnimationListener? = null

    private val isAnimationFinished: Boolean
        get() = finishedState != null

    fun setComponentViewAnimations(initialCenterCircleView: InitialCenterCircleView,
                                   rightCircleView: RightCircleView, sideArcsView: SideArcsView,
                                   topCircleBorderView: TopCircleBorderView, mainCircleView: MainCircleView,
                                   finishedOkCircleView: FinishedOkView, finishedFailureView: FinishedFailureView,
                                   percentIndicatorView: PercentIndicatorView) {
        this.initialCenterCircleView = initialCenterCircleView
        this.rightCircleView = rightCircleView
        this.sideArcsView = sideArcsView
        this.topCircleBorderView = topCircleBorderView
        this.mainCircleView = mainCircleView
        this.finishedOkView = finishedOkCircleView
        this.finishedFailureView = finishedFailureView
        this.percentIndicatorView = percentIndicatorView
        initListeners()
    }

    private fun initListeners() {
        initialCenterCircleView!!.setStateListener(this)
        rightCircleView!!.setStateListener(this)
        sideArcsView!!.setStateListener(this)
        topCircleBorderView!!.setStateListener(this)
        mainCircleView!!.setStateListener(this)
        finishedOkView!!.setStateListener(this)
        finishedFailureView!!.setStateListener(this)
    }

    fun startAnimator() {
        finishedState = null
        initialCenterCircleView!!.showView()
        initialCenterCircleView!!.startTranslateTopAnimation()
        initialCenterCircleView!!.startScaleAnimation()
        rightCircleView!!.showView()
        rightCircleView!!.startSecondaryCircleAnimation()
    }

    fun resetAnimator() {
        initialCenterCircleView!!.hideView()
        rightCircleView!!.hideView()
        sideArcsView!!.hideView()
        topCircleBorderView!!.hideView()
        mainCircleView!!.hideView()
        finishedOkView!!.hideView()
        finishedFailureView!!.hideView()
        resetAnimator = true
        startAnimator()
    }

    fun finishOk() {
        finishedState = AnimationState.FINISHED_OK
    }

    fun finishFailure() {
        finishedState = AnimationState.FINISHED_FAILURE
    }

    fun setAnimationListener(animationListener: AnimatedCircleLoadingView.AnimationListener?) {
        this.animationListener = animationListener
    }

    override fun onStateChanged(state: AnimationState) {
        if (resetAnimator) {
            resetAnimator = false
        } else {
            when (state) {
                AnimationState.MAIN_CIRCLE_TRANSLATED_TOP -> onMainCircleTranslatedTop()
                AnimationState.MAIN_CIRCLE_SCALED_DISAPPEAR -> onMainCircleScaledDisappear()
                AnimationState.MAIN_CIRCLE_FILLED_TOP -> onMainCircleFilledTop()
                AnimationState.SIDE_ARCS_RESIZED_TOP -> onSideArcsResizedTop()
                AnimationState.MAIN_CIRCLE_DRAWN_TOP -> onMainCircleDrawnTop()
                AnimationState.FINISHED_OK -> onFinished(state)
                AnimationState.FINISHED_FAILURE -> onFinished(state)
                AnimationState.MAIN_CIRCLE_TRANSLATED_CENTER -> onMainCircleTranslatedCenter()
                AnimationState.ANIMATION_END -> onAnimationEnd()
                else -> {
                }
            }
        }
    }

    private fun onMainCircleTranslatedTop() {
        initialCenterCircleView!!.startTranslateBottomAnimation()
        initialCenterCircleView!!.startScaleDisappear()
    }

    private fun onMainCircleScaledDisappear() {
        initialCenterCircleView!!.hideView()
        sideArcsView!!.showView()
        sideArcsView!!.startRotateAnimation()
        sideArcsView!!.startResizeDownAnimation()
    }

    private fun onSideArcsResizedTop() {
        topCircleBorderView!!.showView()
        topCircleBorderView!!.startDrawCircleAnimation()
        sideArcsView!!.hideView()
    }

    private fun onMainCircleDrawnTop() {
        mainCircleView!!.showView()
        mainCircleView!!.startFillCircleAnimation()
    }

    private fun onMainCircleFilledTop() {
        if (isAnimationFinished) {
           finishedState?.let{
               onStateChanged(it)
           }
            percentIndicatorView!!.startAlphaAnimation()
        } else {
            topCircleBorderView!!.hideView()
            mainCircleView!!.hideView()
            initialCenterCircleView!!.showView()
            initialCenterCircleView!!.startTranslateBottomAnimation()
            initialCenterCircleView!!.startScaleDisappear()
        }
    }

    private fun onFinished(state: AnimationState) {
        topCircleBorderView!!.hideView()
        mainCircleView!!.hideView()
        finishedState = state
        initialCenterCircleView!!.showView()
        initialCenterCircleView!!.startTranslateCenterAnimation()
    }

    private fun onMainCircleTranslatedCenter() {
        if (finishedState == AnimationState.FINISHED_OK) {
            finishedOkView!!.showView()
            finishedOkView!!.startScaleAnimation()
        } else {
            finishedFailureView!!.showView()
            finishedFailureView!!.startScaleAnimation()
        }
        initialCenterCircleView!!.hideView()
    }

    private fun onAnimationEnd() {
        if (animationListener != null) {
            val success = finishedState == AnimationState.FINISHED_OK
            animationListener!!.onAnimationEnd(success)
        }
    }
}