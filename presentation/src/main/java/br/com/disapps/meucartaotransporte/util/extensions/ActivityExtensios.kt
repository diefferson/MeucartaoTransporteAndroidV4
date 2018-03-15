package br.com.disapps.meucartaotransporte.util.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun Activity.toast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun <T> Activity.startActivity(clazz: Class<T>){
    startActivity(Intent(this, clazz))
}

fun <T> Activity.startActivity(clazz: Class<T>, extras : Bundle){
    val intent = Intent(this, clazz)
    intent.putExtras(extras)
    startActivity(intent)
}

fun Activity.inflateView(resource: Int, viewGroup: View): View =
        layoutInflater.inflate(resource, viewGroup.parent as ViewGroup, false)