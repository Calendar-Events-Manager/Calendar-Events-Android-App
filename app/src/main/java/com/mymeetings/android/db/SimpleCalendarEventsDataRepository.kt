package com.mymeetings.android.db

import com.mymeetings.android.model.CalendarEvent

class SimpleCalendarEventsDataRepository() :
    CalendarEventsRepository {

    private val meetings = mutableListOf<CalendarEvent>()

    override suspend fun getUpcomingMeetings(): List<CalendarEvent> {
        return meetings
    }

    override suspend fun addMeeting(calendarEvent: CalendarEvent) {
        meetings.add(calendarEvent)
    }

    override suspend fun addMeetings(calendarEvents: List<CalendarEvent>) {
        this.meetings.addAll(calendarEvents)
    }

    override suspend fun updateMeeting(calendarEvent: CalendarEvent) {
        val meetingToUpdate = meetings.first {
            it.id == calendarEvent.id
        }
        meetingToUpdate.title = calendarEvent.title
        meetingToUpdate.isDeleted = calendarEvent.isDeleted
        meetingToUpdate.startTime = calendarEvent.startTime
        meetingToUpdate.endTime = calendarEvent.endTime
    }

    override suspend fun clearMeetingsData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}