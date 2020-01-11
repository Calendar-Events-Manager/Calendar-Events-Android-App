package com.mymeetings.android.model

import com.mymeetings.android.utils.ClockUtils
import java.util.concurrent.TimeUnit

class CalendarEventAlertManager(private val clockUtils: ClockUtils) {

    private val priorityTime = TimeUnit.MINUTES.toMillis(10)
    private val normalTime = TimeUnit.MINUTES.toMillis(30)


    fun getCalendarEventAlerts(calendarEvents : List<CalendarEvent>) : List<CalendarEventWithViewAlert> {
        return calendarEvents.map {
            getAlertTypeForCalendarEvent(it)
        }
    }

    private fun getAlertTypeForCalendarEvent(calendarEvent: CalendarEvent) : CalendarEventWithViewAlert {

        val viewAlert = when {
            calendarEvent.startTime < clockUtils.currentTimeMillis() -> {
                ViewAlertType.RUNNING
            }
            calendarEvent.startTime > clockUtils.currentTimeMillis() + priorityTime
                    && calendarEvent.priority.value >= CalendarEventPriority.MEDIUM.value -> {
                ViewAlertType.PRIORITY
            }
            calendarEvent.startTime > clockUtils.currentTimeMillis() + normalTime -> {
                ViewAlertType.NORMAL
            }
            else -> ViewAlertType.LOW
        }
        return CalendarEventWithViewAlert(calendarEvent, viewAlert)
    }
}