package com.mymeetings.android.utils

import android.text.format.DateUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ClockUtils {
    fun currentTimeMillis() = System.currentTimeMillis()

    fun getDate(timeInMillis: Long): Date {
        val eventCalendar = Calendar.getInstance()
        eventCalendar.timeInMillis = timeInMillis
        return eventCalendar.time
    }

    fun getTimeLeft(timeInMillis: Long): String =
        DateUtils.getRelativeTimeSpanString(timeInMillis).toString()

    fun getFormattedTime(timeInMillis: Long): String {
        val formatter: DateFormat = SimpleDateFormat("hh:mm a", Locale.UK)
        return formatter.format(getDate(timeInMillis))
    }
}