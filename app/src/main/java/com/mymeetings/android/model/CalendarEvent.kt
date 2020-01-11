package com.mymeetings.android.model

class CalendarEvent(
    val id : Long? = null,
    var title : String,
    var startTime : Long,
    var endTime : Long,
    var priority: CalendarEventPriority = CalendarEventPriority.MEDIUM,
    var isDeleted : Boolean = false) {

    fun delete() {
        isDeleted = true
    }
}

enum class CalendarEventPriority(val value : Int) {
    HIGH(3),
    MEDIUM(2),
    LOW(1)
}
