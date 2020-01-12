package com.mymeetings.android.model

data class CalendarEventWithViewAlert(
    val calendarEvent: CalendarEvent,
    val viewAlertType : ViewAlertType,
    val timeLeftToStart : String,
    val startTime : String,
    val endTime : String
)

enum class ViewAlertType {
    RUNNING,
    PRIORITY,
    NORMAL,
    LOW
}
