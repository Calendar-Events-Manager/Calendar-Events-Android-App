package com.mymeetings.android.utils

import android.text.format.DateUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ClockUtils {
    fun currentTimeMillis() = System.currentTimeMillis()

    fun getTimeLeft(timeInMillis: Long): String =
        DateUtils.getRelativeTimeSpanString(timeInMillis).toString()

    fun getFormattedTime(timeInMillis: Long): String {
        val formatter: DateFormat = SimpleDateFormat("hh:mm a", Locale.UK)
        val eventCalendar = Calendar.getInstance()
        eventCalendar.timeInMillis = timeInMillis
        return formatter.format(eventCalendar.time)
    }
}