package br.com.disapps.meucartaotransporte.ui.line

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.ui.custom.RoundedColorView
import br.com.disapps.meucartaotransporte.util.getCenter
import br.com.disapps.meucartaotransporte.util.setListener
import br.com.disapps.meucartaotransporte.util.getThemePrimaryColor

class TransitionAnimation(private val context: Context,
                          private val window: Window,
                          private val collapsingToolbarBackground : View,
                          private val roundedImage : RoundedColorView) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setTransitions() {

        // Create a new TransitionSet and inflate a transition from the resources
        val inSet = TransitionSet()
        val inflater = TransitionInflater.from(context)
        val transition = inflater
                .inflateTransition(R.transition.arc)

        // Add the inflated transition to the set and set duration and interpolator
        inSet.apply {
            addTransition(transition)
            duration = 300
            // The shared element should not move in linear motion
            interpolator = AccelerateDecelerateInterpolator()
            // Set a listener to create a circular reveal when the transition has ended
            setListener {
                onTransitionEnd {
                    reveal()
                }
            }
        }

        val outSet = TransitionSet()
                .apply {
                    addTransition(transition)
                    duration = 300
                    interpolator = AccelerateDecelerateInterpolator()
                    startDelay = 200
                }

        // Set the shared element transitions of the activity
        window.sharedElementEnterTransition = inSet
        window.sharedElementExitTransition = outSet
        window.sharedElementReturnTransition = outSet
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun reveal() {

        collapsingToolbarBackground.setBackgroundColor(getThemePrimaryColor(context))

        val center = roundedImage.getCenter()
        // create the animator for this view (the start radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(collapsingToolbarBackground, center.first.toInt(),
                center.second.toInt(), 0f, collapsingToolbarBackground.width.toFloat())
                .apply {
                    duration = 400
                    window.statusBarColor = getThemePrimaryColor(context)
                }

        // make the view visible and start the animation
        collapsingToolbarBackground.visibility = View.VISIBLE
        anim.start()
    }

    fun hideView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val center = roundedImage.getCenter()
            // create the animator for this view (the end radius is zero)
            ViewAnimationUtils.createCircularReveal(collapsingToolbarBackground, center.first.toInt(),
                    center.second.toInt(), collapsingToolbarBackground.width.toFloat(), 0f)
                    .apply {
                        duration = 400
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                collapsingToolbarBackground.visibility = View.INVISIBLE
                            }
                        })
                        start()
                    }
        }
    }

}