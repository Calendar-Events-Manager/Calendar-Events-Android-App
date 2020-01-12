package com.mymeetings.android.utils

import android.text.format.DateUtils
import java.util.*


class ClockUtils {
    fun currentTimeMillis() = System.currentTimeMillis()

    fun getTimeLeft(timeInMillis : Long): String = DateUtils.getRelativeTimeSpanString(timeInMillis).toString()

    fun getTimeInHoursAndMins(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val am = calendar.get(Calendar.AM_PM)

        val hourStr = if(hour < 10) {
            "0${hour}"
        } else {
            "$hour"
        }

        val minStr = when {
            min < 1 -> {
                "00"
            }
            min < 10 -> {
                "0${min}"
            }
            else -> {
                "$min"
            }
        }

        val ampmStr = if(am == Calendar.AM) {
            "AM"
        } else {
            "PM"
        }

        return "${hourStr}:${minStr} ${ampmStr}"
    }
}