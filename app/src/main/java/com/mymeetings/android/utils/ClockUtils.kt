package com.mymeetings.android.utils

import android.text.format.DateUtils
import java.util.*


class ClockUtils {
    fun currentTimeMillis() = System.currentTimeMillis()

    fun getTimeLeft(timeInMillis : Long): String = DateUtils.getRelativeTimeSpanString(timeInMillis).toString()

    fun getTimeInHoursAndMins(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis

        val hour = calendar.get(Calendar.HOUR)
        val min = calendar.get(Calendar.MINUTE)
        val amOrPm = calendar.get(Calendar.AM_PM)
        val hourString = getFormattedTimePart(hour)
        val minString = getFormattedTimePart(min)
        val amOrPmString = if(amOrPm == Calendar.AM) {
            "AM"
        } else {
            "PM"
        }

        return "$hourString:$minString $amOrPmString"
    }

    private fun getFormattedTimePart(number: Int) = when {
            number < 1 -> {
                "00"
            }
            number < 10 -> {
                "0${number}"
            }
            else -> {
                "$number"
            }
        }
}