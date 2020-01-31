package com.mymeetings.android.model

import com.mymeetings.android.model.strategies.ViewAlertType

data class CalendarEventAlert(
    val startTime: Long,
    val reminderTime: Long
) {
    fun viewAlertType(currentTimeMillis:Long) = when {
        currentTimeMillis <= startTime -> ViewAlertType.RUNNING
        currentTimeMillis <= reminderTime -> ViewAlertType.PRIORITY
        else -> ViewAlertType.LOW
    }
}