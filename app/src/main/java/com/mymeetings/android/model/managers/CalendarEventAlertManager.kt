package com.mymeetings.android.model.managers

import com.mymeetings.android.model.*
import com.mymeetings.android.utils.ClockUtils
import java.util.concurrent.TimeUnit

class CalendarEventAlertManager(private val clockUtils: ClockUtils) {

    private val priorityTime = TimeUnit.MINUTES.toMillis(10)

    fun getCalendarEventAlerts(calendarEvents: List<CalendarEvent>): List<CalendarEventWithAlert> =
        calendarEvents.map { CalendarEventWithAlert(it, getViewAlert(it)) }


    private fun getViewAlert(calendarEvent: CalendarEvent) = EventViewAlert(
        timeToShowItAsImmediateEvent = calendarEvent.startTime - priorityTime,
        timeToShowItAsRunningEvent = calendarEvent.startTime,
        clockUtils = clockUtils
    )
}