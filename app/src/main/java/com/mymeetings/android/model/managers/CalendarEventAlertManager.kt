package com.mymeetings.android.model.managers

import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.CalendarEventWithViewAlert
import com.mymeetings.android.model.ViewAlertType
import com.mymeetings.android.utils.ClockUtils
import java.util.concurrent.TimeUnit

class CalendarEventAlertManager(private val clockUtils: ClockUtils) {

    private val priorityTime = TimeUnit.MINUTES.toMillis(10)
    private val normalTime = TimeUnit.MINUTES.toMillis(30)


    fun getCalendarEventAlerts(calendarEvents: List<CalendarEvent>): List<CalendarEventWithViewAlert> {
        return calendarEvents.map {
            CalendarEventWithViewAlert(
                it,
                getViewAlertType(it),
                getRelativeTime(it.startTime),
                clockUtils.getTimeInHoursAndMinsWithCurrentDayContext(it.startTime),
                clockUtils.getTimeInHoursAndMinsWithCurrentDayContext(it.endTime)
            )
        }
    }

    private fun getViewAlertType(calendarEvent: CalendarEvent): ViewAlertType {
        return when {
            calendarEvent.startTime < clockUtils.currentTimeMillis() -> {
                ViewAlertType.RUNNING
            }
            calendarEvent.startTime < clockUtils.currentTimeMillis() + priorityTime -> {
                ViewAlertType.PRIORITY
            }
            calendarEvent.startTime < clockUtils.currentTimeMillis() + normalTime -> {
                ViewAlertType.NORMAL
            }
            else -> ViewAlertType.LOW
        }
    }

    private fun getRelativeTime(eventStartTime: Long): String {
        return when {
            eventStartTime < clockUtils.currentTimeMillis() -> {
                "Running"
            }
            eventStartTime - clockUtils.currentTimeMillis() < TimeUnit.MINUTES.toMillis(1) -> {
                "In a minute"
            }
            else -> {
                clockUtils.getTimeLeft(eventStartTime)
            }
        }
    }
}