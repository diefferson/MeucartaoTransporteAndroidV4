package br.com.disapps.meucartaotransporte.util

import android.animation.Animator
import android.app.Activity
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import br.com.disapps.domain.exception.KnownError
import br.com.disapps.meucartaotransporte.R
import br.com.disapps.meucartaotransporte.exception.UiException
import br.com.disapps.meucartaotransporte.ui.settings.dataUsage.DataUsageActivity
import com.airbnb.lottie.LottieAnimationView


fun Activity.getErrorView(uiException: UiException) :View{
    val rootView = this.findViewById<ViewGroup>(android.R.id.content)
    return when(uiException.knownError){

        KnownError.LINK_DOCUMENT_CARD_EXCEPTION -> {
            inflateView(R.layout.error_expected, rootView).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.alert)
                findViewById<TextView>(R.id.description).text = getString(R.string.card_link_message)
                findViewById<Button>(R.id.known_more).setOnClickListener { getCustomChromeTabs().launchUrl(this@getErrorView, Uri.parse(resources.getString(R.string.url_balance_card))) }
            }
        }

        KnownError.INACTIVE_CARD_EXCEPTION ->{
            inflateView(R.layout.error_expected,rootView ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.vish)
                findViewById<TextView>(R.id.description).text = getString(R.string.inactive_card)
                findViewById<Button>(R.id.known_more).setOnClickListener { finish() }
                findViewById<Button>(R.id.known_more).text = getString(R.string.back)
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.vish)
            }
        }

        KnownError.INVALID_CARD_EXCEPTION -> {
            inflateView(R.layout.error_expected,rootView ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.vish)
                findViewById<TextView>(R.id.description).text = getString(R.string.invalid_card)
                findViewById<Button>(R.id.known_more).setOnClickListener { finish() }
                findViewById<Button>(R.id.known_more).text = getString(R.string.back)
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.vish)
            }
        }

        KnownError.INVALID_LINE_EXCEPTION-> {
            inflateView(R.layout.error_expected,rootView ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.vish)
                findViewById<TextView>(R.id.description).text = getString(R.string.invalid_line)
                findViewById<Button>(R.id.known_more).setOnClickListener { finish() }
                findViewById<Button>(R.id.known_more).text = getString(R.string.back)
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.vish)
            }
        }

        KnownError.INVALID_DOCUMENT_EXCEPTION-> {
            inflateView(R.layout.error_expected,rootView ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.vish)
                findViewById<TextView>(R.id.description).text = getString(R.string.invalid_cpf)
                findViewById<Button>(R.id.known_more).setOnClickListener { finish() }
                findViewById<Button>(R.id.known_more).text = getString(R.string.back)
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.vish)
            }
        }

        KnownError.URBS_RETURN_ERROR->{
            inflateView(R.layout.error_expected,rootView ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.not_fault)
                findViewById<TextView>(R.id.description).text = uiException.message
                findViewById<Button>(R.id.known_more).setOnClickListener { getCustomChromeTabs().launchUrl(this@getErrorView, Uri.parse(resources.getString(R.string.url_balance_card))) }
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.not_fault)
            }
        }

        KnownError.CARD_EXISTS_EXCEPTION ->{
            inflateView(R.layout.error_expected,rootView ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.funny)
                findViewById<TextView>(R.id.description).text = uiException.message
                findViewById<LottieAnimationView>(R.id.animation).setAnimation(R.raw.funy)
                findViewById<Button>(R.id.known_more).text = getString(R.string.back)
                findViewById<Button>(R.id.known_more).setOnClickListener { finish() }
            }
        }

        else -> {
            inflateView(R.layout.error_expected,rootView ).apply {
                findViewById<TextView>(R.id.title).text = getString(R.string.gave_bad)
                findViewById<TextView>(R.id.description).text = getString(R.string.network_generic_error)
                findViewById<Button>(R.id.known_more).text = getString(R.string.tray_again)
                findViewById<Button>(R.id.known_more).setOnClickListener { recreate() }
            }
        }
    }

}

fun Activity.getProgressView() :View{
    val rootView = this.findViewById<ViewGroup>(android.R.id.content)
    return inflateView(R.layout.progress_view, rootView)
}

fun Activity.getLoadingView() :View{
    val rootView = this.findViewById<ViewGroup>(android.R.id.content)
    return inflateView(R.layout.loading_view, rootView)
}
fun Activity.getDownloadDataView() : View{
    val rootView = findViewById<ViewGroup>(android.R.id.content)
    val view= inflateView(R.layout.download_data, rootView )
    view.findViewById<Button>(R.id.download).setOnClickListener { DataUsageActivity.launch(this)}
    return view
}

fun Activity.getEmptyView(message: String): View {
    val rootView = findViewById<ViewGroup>(android.R.id.content)
    val layout = inflateView(R.layout.empty_view, rootView )
    layout.findViewById<TextView>(R.id.empty_text).text = message
    return layout
}

fun Activity.getOfflineView():View{

    val rootView = findViewById<ViewGroup>(android.R.id.content)
    val view = inflateView(R.layout.offline_view, rootView)
    val button = view.findViewById<Button>(R.id.back)
    val animation = view.findViewById<LottieAnimationView>(R.id.animation_view)
    animation.addAnimatorListener(object : Animator.AnimatorListener{
        override fun onAnimationRepeat(animation: Animator?) {}
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationStart(animation: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {
            view.findViewById<TextView>(R.id.offlineText).visibility = View.VISIBLE
            button.apply {
                visibility = View.VISIBLE
                setOnClickListener { finish() }
            }
        }
    })

    return view
}