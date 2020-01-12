package com.mymeetings.android.model.extensions

import com.mymeetings.android.db.CalendarEventsDbModel
import com.mymeetings.android.model.CalendarEvent

fun CalendarEvent.toDbModel() = CalendarEventsDbModel(
    id = this.id,
    title = this.title,
    startTime = this.startTime,
    endTime = this.endTime,
    isDone = this.isDeleted
)

fun CalendarEventsDbModel.toDomainModel() = CalendarEvent(
    id = this.id,
    title = this.title,
    startTime = this.startTime,
    endTime = this.endTime,
    isDeleted = this.isDone
)

fun List<CalendarEvent>.toDbModelCollection() = this.map { it.toDbModel() }

fun List<CalendarEventsDbModel>.toDomainModelCollection() = this.map { it.toDomainModel() }