package br.com.disapps.meucartaotransporte.util

import br.com.disapps.domain.model.Schedule
import java.util.*

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
