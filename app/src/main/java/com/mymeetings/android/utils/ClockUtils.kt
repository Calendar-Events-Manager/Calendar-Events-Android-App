package com.mymeetings.android.utils

import android.text.format.DateUtils
import java.util.*
import java.util.concurrent.TimeUnit


class ClockUtils {
    fun currentTimeMillis() = System.currentTimeMillis()

    fun getTimeLeft(timeInMillis : Long): String = DateUtils.getRelativeTimeSpanString(timeInMillis).toString()

    fun getTimeInHoursAndMins(timeInMillis: Long): String {
        val todayCalendar = Calendar.getInstance()
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0)
        todayCalendar.set(Calendar.MINUTE, 0)
        todayCalendar.set(Calendar.SECOND, 0)
        todayCalendar.set(Calendar.MILLISECOND, 0)

        val eventCalendar = Calendar.getInstance()
        eventCalendar.timeInMillis = timeInMillis

        val dayFromToday = ((eventCalendar.timeInMillis-todayCalendar.timeInMillis)/TimeUnit.DAYS.toMillis(1)).toInt()
        val hour = eventCalendar.get(Calendar.HOUR)
        val min = eventCalendar.get(Calendar.MINUTE)
        val amOrPm = eventCalendar.get(Calendar.AM_PM)

        val dayFromTodayStr = if(dayFromToday == 0) "" else "(${dayFromToday.toString()}) "
        val hourString = getFormattedTimePart(hour, true)
        val minString = getFormattedTimePart(min, false)
        val amOrPmString = if(amOrPm == Calendar.AM) {
            "AM"
        } else {
            "PM"
        }

        return "$dayFromTodayStr$hourString:$minString $amOrPmString"
    }

    private fun getFormattedTimePart(number: Int, isHour : Boolean) = when {
            number == 0 -> {
                if(isHour) {
                    "12"
                } else {
                    "00"
                }
            }
            number < 10 -> {
                "0${number}"
            }
            else -> {
                "$number"
            }
        }
}