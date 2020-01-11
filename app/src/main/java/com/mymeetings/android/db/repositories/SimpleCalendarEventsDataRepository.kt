package com.mymeetings.android.db.repositories

import com.mymeetings.android.model.CalendarEvent

class SimpleCalendarEventsDataRepository() :
    CalendarEventsRepository {

    private val calendarEvents = mutableListOf<CalendarEvent>()

    override suspend fun getUpcomingCalendarEvents(): List<CalendarEvent> {
        return calendarEvents
    }

    override suspend fun addCalendarEvent(calendarEvent: CalendarEvent) {
        calendarEvents.add(calendarEvent)
    }

    override suspend fun addCalendarEvents(calendarEvents: List<CalendarEvent>) {
        this.calendarEvents.addAll(calendarEvents)
    }

    override suspend fun updateCalendarEvents(calendarEvent: CalendarEvent) {
        val meetingToUpdate = calendarEvents.first {
            it.id == calendarEvent.id
        }
        meetingToUpdate.title = calendarEvent.title
        meetingToUpdate.isDeleted = calendarEvent.isDeleted
        meetingToUpdate.startTime = calendarEvent.startTime
        meetingToUpdate.endTime = calendarEvent.endTime
    }

    override suspend fun clearCalendarEvents() {
        calendarEvents.clear()
    }
}