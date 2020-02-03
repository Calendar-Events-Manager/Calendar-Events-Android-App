package com.mymeetings.android.model

data class CalendarEventAlertUIModel(
    val id : Long?,
    val title : String,
    val eventTimeLine : String,
    val timeLeft : String,
    val reminderTime : Long,
    val startTime: Long
) {
    fun getViewAlertType(currentTimeMillis:Long) = when {
        currentTimeMillis <= startTime -> ViewAlertType.RUNNING
        currentTimeMillis <= reminderTime -> ViewAlertType.PRIORITY
        else -> ViewAlertType.LOW
    }
}