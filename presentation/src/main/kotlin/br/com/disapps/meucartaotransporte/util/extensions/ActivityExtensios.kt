package br.com.disapps.meucartaotransporte.util.extensions

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import br.com.disapps.meucartaotransporte.R
import android.widget.RelativeLayout.CENTER_IN_PARENT
import kotlinx.android.synthetic.main.include_container.*
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.support.design.widget.Snackbar

fun Activity.toast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.inflateView(resource: Int, viewGroup: View): View =
        layoutInflater.inflate(resource, viewGroup.parent as ViewGroup, false)

fun Activity.validateConnection(): Boolean {
    val connected: Boolean
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connected = (connectivityManager.activeNetworkInfo != null
            && connectivityManager.activeNetworkInfo.isAvailable
            && connectivityManager.activeNetworkInfo.isConnected)
    return connected
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
