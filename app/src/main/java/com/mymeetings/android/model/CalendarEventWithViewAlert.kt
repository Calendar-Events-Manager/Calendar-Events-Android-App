package com.mymeetings.android.model

data class CalendarEventWithViewAlert(
    val calendarEvent: CalendarEvent,
    val viewAlertType : ViewAlertType,
    val relativeTime : String,
    val startToEndTime : String
)

enum class ViewAlertType {
    RUNNING,
    PRIORITY,
    NORMAL,
    LOW
}
