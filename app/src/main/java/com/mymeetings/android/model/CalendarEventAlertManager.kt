package com.mymeetings.android.model

import com.mymeetings.android.utils.ClockUtils
import java.util.*
import java.util.concurrent.TimeUnit

class CalendarEventAlertManager(private val clockUtils: ClockUtils) {

    private val priorityTime = TimeUnit.MINUTES.toMillis(10)
    private val normalTime = TimeUnit.MINUTES.toMillis(30)


    fun getCalendarEventAlerts(calendarEvents : List<CalendarEvent>) : List<Pair<CalendarEvent, AlertType>> {
        return calendarEvents.map {
            Pair(it, getAlertTypeForCalendarEvent(it))
        }
    }

    private fun getAlertTypeForCalendarEvent(calendarEvent: CalendarEvent) : AlertType {

        return when {
            calendarEvent.startTime < clockUtils.currentTimeMillis() -> {
                AlertType.RUNNING
            }
            calendarEvent.startTime > clockUtils.currentTimeMillis() + priorityTime
                    && calendarEvent.priority.value >= CalendarEventPriority.MEDIUM.value -> {
                AlertType.PRIORITY
            }
            calendarEvent.startTime > clockUtils.currentTimeMillis() + normalTime -> {
                AlertType.NORMAL
            }
            else -> AlertType.LOW
        }

    }
}

enum class AlertType {
    RUNNING,
    PRIORITY,
    NORMAL,
    LOW
}