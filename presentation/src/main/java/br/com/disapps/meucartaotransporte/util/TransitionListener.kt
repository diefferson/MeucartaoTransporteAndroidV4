package br.com.disapps.meucartaotransporte.util

import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.Transition


@RequiresApi(Build.VERSION_CODES.KITKAT)
fun Transition.setListener(init: TransitionListenerHelper.() -> Unit) {
    val listener = TransitionListenerHelper()
    listener.init()
    this.addListener(listener)
}

private typealias CustomTransitionListener = (Transition) -> Unit

@RequiresApi(Build.VERSION_CODES.KITKAT)
class TransitionListenerHelper : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition) {
        transitionEnd?.invoke(transition)
    }

    override fun onTransitionResume(transition: Transition) {
        transitionResume?.invoke(transition)
    }

    override fun onTransitionPause(transition: Transition) {
        transitionPause?.invoke(transition)
    }

    override fun onTransitionCancel(transition: Transition) {
        transitionCancel?.invoke(transition)
    }

    override fun onTransitionStart(transition: Transition) {
        transitionStart?.invoke(transition)
    }

    private var transitionStart: CustomTransitionListener? = null

    fun onTransitionStart(onTransitionStart: CustomTransitionListener) {
        transitionStart = onTransitionStart
    }

    private var transitionPause: CustomTransitionListener? = null

    fun onTransitionPause(onTransitionRepeat: CustomTransitionListener) {
        transitionPause = onTransitionRepeat
    }

    private var transitionResume: CustomTransitionListener? = null

    fun onTransitionResume(onTransitionResume: CustomTransitionListener) {
        transitionPause = onTransitionResume
    }

    private var transitionCancel: CustomTransitionListener? = null

    fun onTransitionCancel(onTransitionCancel: CustomTransitionListener) {
        transitionCancel = onTransitionCancel
    }

    private var transitionEnd: CustomTransitionListener? = null

    fun onTransitionEnd(onTransitionEnd: CustomTransitionListener) {
        transitionEnd = onTransitionEnd
    }
}