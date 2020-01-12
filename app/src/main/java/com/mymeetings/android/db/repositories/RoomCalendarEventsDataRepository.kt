package com.mymeetings.android.db.repositories

import com.mymeetings.android.db.CalendarEventsDao
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.extensions.toDbModel
import com.mymeetings.android.model.extensions.toDbModelCollection
import com.mymeetings.android.model.extensions.toDomainModelCollection
import com.mymeetings.android.utils.ClockUtils

class RoomCalendarEventsDataRepository(
    private val calendarEventsDao: CalendarEventsDao,
    private val clockUtils: ClockUtils
) : CalendarEventsRepository {

    override suspend fun getRelevantCalendarEvents() = calendarEventsDao
        .getCalendarEventsEndingAfter(clockUtils.currentTimeMillis())
        .toDomainModelCollection()

    override suspend fun addCalendarEvent(calendarEvent: CalendarEvent) {
        calendarEventsDao.addCalendarEvent(calendarEvent.toDbModel())
    }

    override suspend fun addCalendarEvents(calendarEvents: List<CalendarEvent>) {
        calendarEventsDao.addCalendarEvents(calendarEvents.toDbModelCollection())
    }

    override suspend fun updateCalendarEvents(calendarEvent: CalendarEvent) {
        calendarEventsDao.updateCalendarEvent(calendarEvent.toDbModel())
    }

    override suspend fun clearCalendarEvents() {
        calendarEventsDao.purgeCalendarEvents()
    }
}