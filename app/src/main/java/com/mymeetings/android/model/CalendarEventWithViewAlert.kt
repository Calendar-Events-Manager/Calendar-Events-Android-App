package com.mymeetings.android.model

data class CalendarEventWithViewAlert(
    val calendarEvent: CalendarEvent,
    val viewAlertType : ViewAlertType
)

enum class ViewAlertType {
    RUNNING,
    PRIORITY,
    NORMAL,
    LOW
}
