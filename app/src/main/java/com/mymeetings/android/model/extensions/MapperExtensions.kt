package com.mymeetings.android.model.extensions

import com.mymeetings.android.db.CalendarEventDbModel
import com.mymeetings.android.model.CalendarEventsConfigurations
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.CalendarEventAlertUIModel
import com.mymeetings.android.utils.ClockUtils

fun CalendarEvent.toDbModel() = CalendarEventDbModel(
    id = this.id,
    title = this.title,
    startTime = this.startTime,
    endTime = this.endTime,
    isDone = this.isDeleted
)

fun CalendarEventDbModel.toDomainModel() = CalendarEvent(
    id = this.id,
    title = this.title,
    startTime = this.startTime,
    endTime = this.endTime,
    isDeleted = this.isDone
)

fun List<CalendarEvent>.toDbModelCollection() = this.map { it.toDbModel() }

fun List<CalendarEventDbModel>.toDomainModelCollection() = this.map { it.toDomainModel() }

fun List<CalendarEvent>.toUiModelCollection(clockUtils: ClockUtils) = this.map { it.toCalendarEventUIModel(clockUtils) }

fun CalendarEvent.toCalendarEventUIModel(clockUtils: ClockUtils): CalendarEventAlertUIModel {
    val startTimeToEndTimeString =
        "${clockUtils.getFormattedTime(this.startTime)} - ${clockUtils.getFormattedTime(this.endTime)}"
    val timeLeft = clockUtils.getTimeLeft(this.startTime)

    return CalendarEventAlertUIModel(
        id = this.id,
        title = this.title,
        eventTimeLine = startTimeToEndTimeString,
        timeLeft = timeLeft,
        startTime = startTime,
        reminderTime = startTime - CalendarEventsConfigurations.REMINDER_BUFFER_IN_MILLIS
    )
}

