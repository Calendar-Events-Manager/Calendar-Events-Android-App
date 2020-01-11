package com.mymeetings.android.db.repositories

import com.mymeetings.android.db.CalendarEventsDao
import com.mymeetings.android.db.CalendarEventsDbModel
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.utils.ClockUtils

class RoomCalendarEventsDataRepository(
    private val calendarEventsDao: CalendarEventsDao,
    private val clockUtils: ClockUtils
) : CalendarEventsRepository {

    override suspend fun getUpcomingCalendarEvents(): List<CalendarEvent> {
        val currentTime = clockUtils.currentTimeMillis()
        return calendarEventsDao.getCalendarEventsBy(currentTime).map {
            CalendarEvent(
                id = it.id,
                title = it.title,
                startTime = it.startTime,
                endTime = it.endTime,
                isDeleted = it.isDone
            )
        }
    }

    override suspend fun addCalendarEvent(calendarEvent: CalendarEvent) {
        calendarEventsDao.addCalendarEvent(
            CalendarEventsDbModel(
                title = calendarEvent.title,
                startTime = calendarEvent.startTime,
                endTime = calendarEvent.endTime,
                isDone = calendarEvent.isDeleted
            )
        )
    }

    override suspend fun addCalendarEvents(calendarEvents: List<CalendarEvent>) {
        calendarEventsDao.addCalendarEvents(* calendarEvents.map { meeting ->
            CalendarEventsDbModel(
                title = meeting.title,
                startTime = meeting.startTime,
                endTime = meeting.endTime,
                isDone = meeting.isDeleted
            )
        }.toTypedArray())
    }

    override suspend fun updateCalendarEvents(calendarEvent: CalendarEvent) {
        calendarEventsDao.updateCalendarEvent(
            CalendarEventsDbModel(
                id = calendarEvent.id,
                title = calendarEvent.title,
                startTime = calendarEvent.startTime,
                endTime = calendarEvent.endTime,
                isDone = calendarEvent.isDeleted
            )
        )
    }

    override suspend fun clearCalendarEvents() {
        calendarEventsDao.purgeCalendarEvents()
    }
}