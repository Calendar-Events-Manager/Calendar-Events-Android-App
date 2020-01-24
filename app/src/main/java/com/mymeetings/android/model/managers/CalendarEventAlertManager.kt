package com.mymeetings.android.model.managers

import com.mymeetings.android.model.*
import com.mymeetings.android.utils.ClockUtils
import java.util.concurrent.TimeUnit

class CalendarEventAlertManager(private val clockUtils: ClockUtils) {

    private val reminderBufferInMillis = TimeUnit.MINUTES.toMillis(10)

    fun getCalendarEventAlerts(calendarEvents: List<CalendarEvent>): List<CalendarEventWithAlert> =
        calendarEvents.map { CalendarEventWithAlert(it, it.startTime - reminderBufferInMillis) }
}