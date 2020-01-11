package com.mymeetings.android.db

import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.utils.ClockUtils

class RoomCalendarEventsDataRepository(
    private val calendarEventsDao: CalendarEventsDao,
    private val clockUtils: ClockUtils
) : CalendarEventsRepository {

    override suspend fun getUpcomingMeetings(): List<CalendarEvent> {
        val currentTime = clockUtils.currentTimeMillis()
        return calendarEventsDao.getMeetingsBy(currentTime).map {
            CalendarEvent(
                id = it.id,
                title = it.title,
                startTime = it.startTime,
                endTime = it.endTime,
                isDeleted = it.isDone
            )
        }
    }

    override suspend fun addMeeting(calendarEvent: CalendarEvent) {
        calendarEventsDao.addMeeting(
            CalendarEventsDbModel(
                title = calendarEvent.title,
                startTime = calendarEvent.startTime,
                endTime = calendarEvent.endTime,
                isDone = calendarEvent.isDeleted
            )
        )
    }

    override suspend fun addMeetings(calendarEvents: List<CalendarEvent>) {
        calendarEventsDao.addMeetings(* calendarEvents.map { meeting ->
            CalendarEventsDbModel(
                title = meeting.title,
                startTime = meeting.startTime,
                endTime = meeting.endTime,
                isDone = meeting.isDeleted
            )
        }.toTypedArray())
    }

    override suspend fun updateMeeting(calendarEvent: CalendarEvent) {
        calendarEventsDao.updateMeeting(
            CalendarEventsDbModel(
                id = calendarEvent.id,
                title = calendarEvent.title,
                startTime = calendarEvent.startTime,
                endTime = calendarEvent.endTime,
                isDone = calendarEvent.isDeleted
            )
        )
    }

    override suspend fun clearMeetingsData() {
        calendarEventsDao.purgeMeetings()
    }
}