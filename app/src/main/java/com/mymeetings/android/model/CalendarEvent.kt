package com.mymeetings.android.model

class CalendarEvent(
    val id : Long? = null,
    var title : String,
    var startTime : Long,
    var endTime : Long,
    var priority: CalendarEventPriority = CalendarEventPriority.LOW,
    var isDeleted : Boolean = false) {

    fun delete() {
        isDeleted = true
    }
}

enum class CalendarEventPriority {
    HIGH,
    MEDIUM,
    LOW
}
