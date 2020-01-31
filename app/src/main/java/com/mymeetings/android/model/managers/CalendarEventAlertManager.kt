package com.mymeetings.android.model.managers

import com.mymeetings.android.model.*
import java.util.concurrent.TimeUnit

class CalendarEventAlertManager {

    private val reminderBufferInMillis = TimeUnit.MINUTES.toMillis(10)

    fun getCalendarEventAlert(calendarEvent: CalendarEvent) : CalendarEventAlert =
        CalendarEventAlert(
            startTime = calendarEvent.startTime,
            reminderTime = calendarEvent.startTime - reminderBufferInMillis)
}