package br.com.disapps.meucartaotransporte.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.text.Html
import android.text.Spanned

fun Activity.toast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

@Suppress("DEPRECATION")
fun fromHtml(html: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}

fun Activity.inflateView(resource: Int, viewGroup: View, attachToRoot : Boolean = false): View =
        layoutInflater.inflate(resource, viewGroup.parent as ViewGroup, attachToRoot)

fun Activity.validateConnection(): Boolean {
    val connected: Boolean
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connected = (connectivityManager.activeNetworkInfo != null
            && connectivityManager.activeNetworkInfo.isAvailable
            && connectivityManager.activeNetworkInfo.isConnected)
    return connected
}

fun Activity.alertNeutral(message: String) {
    val bld = android.app.AlertDialog.Builder(this)
    bld.setMessage(message)
    bld.setNeutralButton("OK", null)
    bld.create().show()
}

fun Activity.alert( titulo: String, mensagem: String) {

    val builder = AlertDialog.Builder(this)
    builder.setTitle(titulo)
    builder.setMessage(mensagem)
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ -> }
    val alert = builder.create()
    alert.show()
}
