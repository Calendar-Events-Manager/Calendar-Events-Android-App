package com.mymeetings.android.utils

import android.text.format.DateUtils
import java.util.*
import java.util.concurrent.TimeUnit


class ClockUtils {
    fun currentTimeMillis() = System.currentTimeMillis()

    fun getTimeLeft(timeInMillis : Long): String = DateUtils.getRelativeTimeSpanString(timeInMillis).toString()

    fun getTimeInHoursAndMins(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis

        val hour = calendar.get(Calendar.HOUR)
        val min = calendar.get(Calendar.MINUTE)
        val amOrPm = calendar.get(Calendar.AM_PM)
        val hourString = getFormattedTimePart(hour, true)
        val minString = getFormattedTimePart(min, false)
        val amOrPmString = if(amOrPm == Calendar.AM) {
            "AM"
        } else {
            "PM"
        }

        return "$hourString:$minString $amOrPmString"
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