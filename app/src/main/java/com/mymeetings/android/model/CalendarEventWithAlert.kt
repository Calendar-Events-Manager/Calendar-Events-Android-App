package com.mymeetings.android.model

import com.mymeetings.android.model.strategies.ViewAlertType

data class CalendarEventWithAlert(
    val calendarEvent: CalendarEvent,
    val reminderTime: Long
) {
    fun viewAlertType(currentTimeMillis:Long) = when {
        currentTimeMillis <= calendarEvent.startTime -> ViewAlertType.RUNNING
        currentTimeMillis <= reminderTime -> ViewAlertType.PRIORITY
        else -> ViewAlertType.LOW
    }
}