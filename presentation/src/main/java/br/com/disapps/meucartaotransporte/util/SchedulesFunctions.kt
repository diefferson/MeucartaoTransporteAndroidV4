package br.com.disapps.meucartaotransporte.util

import android.content.Context
import br.com.disapps.domain.model.Schedule
import br.com.disapps.meucartaotransporte.R
import java.util.*
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import br.com.disapps.domain.model.City
import br.com.disapps.domain.model.PeriodUpdate
import java.text.SimpleDateFormat

fun formatDate(date : Date):String{
     val df = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault() )
    return df.format(date)
}

fun validateSchedule(schedule: Schedule, day :Int): Boolean {

    val initialTime = validateWeekday(day)

    val time = schedule.time.split(":")

    val cal = Calendar.getInstance()

    cal.set(Calendar.HOUR_OF_DAY, time[0].toInt())
    cal.set(Calendar.MINUTE, time[1].toInt())

    if (cal.get(Calendar.HOUR_OF_DAY) == 0) {
        cal.set(Calendar.HOUR_OF_DAY, 24)
    }

    if (cal.after(initialTime)) {
        return true
    }

    return false
}

private fun validateWeekday(day : Int): Calendar  {
    val actualDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    when (day) {
        3 -> //sunday
            return if (actualDay == 1) {
                setInitialTime()
            } else {
                setInitialTime(true)
            }
        2 -> //saturday
            return if (actualDay == 7) {
                setInitialTime()
            } else {
                setInitialTime(true)
            }
        else -> return if (actualDay in 2..6) {//util day
            setInitialTime()
        } else {
            setInitialTime(true)
        }
    }
}

fun getDayWeek(): Int {
    val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    if (day == 1) {
        return 2
    }
    return if (day == 7) {
        1
    } else 0
}

private fun setInitialTime(isMidnight:Boolean = false): Calendar {
    return  if(isMidnight){
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
    }else{
        Calendar.getInstance()
    }
}

fun getCustomTheme(colorName:String, hasActionBar: Boolean = false): Int{
    return when (colorName) {
        "blue" -> if(hasActionBar) R.style.AppTheme_Blue else R.style.AppTheme_Blue_NoActionBar
        "lightBlue" ->  if(hasActionBar) R.style.AppTheme_BlueLight else R.style.AppTheme_BlueLight_NoActionBar
        "red" ->   if(hasActionBar) R.style.AppTheme_Red else R.style.AppTheme_Red_NoActionBar
        "grey" ->  if(hasActionBar) R.style.AppTheme_Grey else R.style.AppTheme_Grey_NoActionBar
        "green" -> if(hasActionBar) R.style.AppTheme_Green else R.style.AppTheme_Green_NoActionBar
        "yellow" ->  if(hasActionBar) R.style.AppTheme_Yellow else R.style.AppTheme_Yellow_NoActionBar
        "orange" ->  if(hasActionBar) R.style.AppTheme_Orange else R.style.AppTheme_Orange_NoActionBar
        "white" ->  if(hasActionBar) R.style.AppTheme_White else R.style.AppTheme_White_NoActionBar
        "ccd" ->  if(hasActionBar) R.style.AppTheme_Ccd else R.style.AppTheme_Ccd_NoActionBar
        else ->  if(hasActionBar) R.style.AppTheme else R.style.AppTheme_NoActionBar
    }
}

fun getBusColor(context: Context,colorName:String) : Int{
    return when (colorName) {
        "blue" ->  ContextCompat.getColor(context, R.color.line_blue)
        "lightBlue" ->  ContextCompat.getColor(context,R.color.line_blue_light)
        "red" ->  ContextCompat.getColor(context,R.color.line_red)
        "grey" ->  ContextCompat.getColor(context,R.color.line_grey)
        "green" ->  ContextCompat.getColor(context,R.color.line_green)
        "yellow" ->  ContextCompat.getColor(context,R.color.line_yellow)
        "orange" ->  ContextCompat.getColor(context,R.color.line_orange)
        "white" ->  ContextCompat.getColor(context,R.color.line_white)
        "ccd" -> ContextCompat.getColor(context, R.color.line_ccd)
        else -> ContextCompat.getColor(context,R.color.line_blue_light)
    }
}

fun getThemeAccentColor(context: Context): Int {
    val value = TypedValue()
    context.theme.resolveAttribute(R.attr.colorAccent, value, true)
    return value.data
}

fun getThemePrimaryColor(context: Context): Int {
    val value = TypedValue()
    context.theme.resolveAttribute(R.attr.colorPrimary, value, true)
    return value.data
}

fun getAccentColorStateList(context: Context): ColorStateList {
    val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf(-android.R.attr.state_checked))
    val colors =  intArrayOf(getThemeAccentColor(context),ContextCompat.getColor(context,R.color.navigation_inactive))
    return  ColorStateList(states, colors)
}

fun getDayName(context: Context, day : Int) : String{
    return when(day){
        1 -> context.getString(R.string.useful_day)
        2 -> context.getString(R.string.saturday)
        else -> context.getString(R.string.sunday)
    }
}

fun getPeriodItems(context: Context) : Array<String>{
    return arrayOf(context.getString(R.string.weekly),
            context.getString(R.string.monthly),
            context.getString(R.string.yearly),
            context.getString(R.string.manual))
}

fun getPeriodPosition(context: Context, period: String?): Int {
    return when (period) {
        context.getString(R.string.weekly) -> 0
        context.getString(R.string.monthly) -> 1
        context.getString(R.string.yearly) -> 2
        context.getString(R.string.manual) -> 3
        else -> 0
    }
}

fun getCity(lineType :String) : City{
    return if(lineType.toUpperCase() == "METROPOLITANA"){
        City.MET
    }else{
        City.CWB
    }
}




