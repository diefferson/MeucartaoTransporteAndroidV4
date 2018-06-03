package br.com.disapps.meucartaotransporte.util

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

object PermissionsUtils{

    const val WRITE_STORAGE_CODE = 5050
    const val ACCESS_LOCATION_CODE = 4040
    const val WRITE_STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val ACCESS_LOCATION_PERMISSION = android.Manifest.permission.ACCESS_FINE_LOCATION

    fun requestPermission(activity:Activity, permission:String, code: Int) :Boolean{
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), code)
            return false
        }
        return true
    }

}