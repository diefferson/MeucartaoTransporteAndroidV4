package br.com.disapps.meucartaotransporte.base.widgets.busSchedules

import android.content.Context

object BusSchedulesPreferences{

    private const val LINE_CODE= "lineCode"
    private const val LINE_NAME= "lineName"
    private const val STOP_CODE = "stopCode"
    private const val STOP_NAME = "stopName"
    private const val LINE_COLOR = "lineColore"

    fun getLineCode(context:Context, prefsName:String, prefKey:String): String {
        val prefs = context.getSharedPreferences(prefsName, 0)
        val lineCode = prefs.getString(prefKey + LINE_CODE, null)
        return lineCode ?: ""
    }

    fun getLineName(context:Context, prefsName:String, prefKey:String): String {
        val prefs = context.getSharedPreferences(prefsName, 0)
        val lineName = prefs.getString(prefKey + LINE_NAME, null)
        return lineName ?: ""
    }

    fun getLineColor(context:Context, prefsName:String, prefKey:String): String {
        val prefs = context.getSharedPreferences(prefsName, 0)
        val lineName = prefs.getString(prefKey + LINE_COLOR, null)
        return lineName ?: ""
    }

    fun getStopCode(context:Context, prefsName:String, prefKey:String): String {
        val prefs = context.getSharedPreferences(prefsName, 0)
        val stopCode = prefs.getString(prefKey + STOP_CODE, null)
        return stopCode ?: ""
    }

    fun getStopName(context:Context, prefsName:String, prefKey:String): String {
        val prefs = context.getSharedPreferences(prefsName, 0)
        val stopName = prefs.getString(prefKey + STOP_NAME, null)
        return stopName ?: ""
    }

    fun setLineCode(context:Context, prefsName:String, prefKey:String, lineCode:String){
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.putString(prefKey + LINE_CODE, lineCode)
        prefs.apply()
    }

    fun setLineName(context:Context, prefsName:String, prefKey:String, lineName:String){
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.putString(prefKey + LINE_NAME, lineName)
        prefs.apply()
    }

    fun setLineColor(context:Context, prefsName:String, prefKey:String, lineColor:String){
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.putString(prefKey + LINE_COLOR, lineColor)
        prefs.apply()
    }

    fun setStopCode(context:Context, prefsName:String, prefKey:String, stopCode:String) {
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.putString(prefKey + STOP_CODE, stopCode)
        prefs.apply()
    }

    fun setStopName(context:Context, prefsName:String, prefKey:String, stopName:String) {
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.putString(prefKey + STOP_NAME, stopName)
        prefs.apply()
    }

    fun deleteLineCode(context:Context, prefsName:String, prefKey:String){
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.remove(prefKey + LINE_CODE)
        prefs.apply()
    }

    fun deleteLineName(context:Context, prefsName:String, prefKey:String){
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.remove(prefKey + LINE_NAME)
        prefs.apply()
    }

    fun deleteLineColor(context:Context, prefsName:String, prefKey:String){
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.remove(prefKey + LINE_COLOR)
        prefs.apply()
    }

    fun deleteStopCode(context:Context, prefsName:String, prefKey:String) {
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.remove(prefKey + STOP_CODE)
        prefs.apply()
    }

    fun deleteStopName(context:Context, prefsName:String, prefKey:String) {
        val prefs = context.getSharedPreferences(prefsName, 0).edit()
        prefs.remove(prefKey + STOP_NAME)
        prefs.apply()
    }
}