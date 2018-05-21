package br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.component

import android.content.Context
import android.view.View
import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.animator.AnimationState
import br.com.disapps.meucartaotransporte.ui.custom.animateCircleLoadingView.exception.NullStateListenerException

/**
 * @author jlmd
 */
abstract class ComponentViewAnimation(context: Context, protected val parentWidth: Int, protected val mainColor: Int,
                                      protected val secondaryColor: Int) : View(context) {

    protected var parentCenter: Float = 0.toFloat()
    protected var circleRadius: Float = 0.toFloat()
    protected var strokeWidth: Int = 0
    private var stateListener: StateListener? = null

    init {
        init()
    }

    private fun init() {
        hideView()
        circleRadius = (parentWidth / 10).toFloat()
        parentCenter = (parentWidth / 2).toFloat()
        strokeWidth = 12 * parentWidth / 700
    }

    fun hideView() {
        visibility = View.GONE
    }

    fun showView() {
        visibility = View.VISIBLE
    }

    fun setState(state: AnimationState) {
        if (stateListener != null) {
            stateListener!!.onStateChanged(state)
        } else {
            throw NullStateListenerException()
        }
    }

    fun setStateListener(stateListener: StateListener) {
        this.stateListener = stateListener
    }

    interface StateListener {

        fun onStateChanged(state: AnimationState)
    }
}
