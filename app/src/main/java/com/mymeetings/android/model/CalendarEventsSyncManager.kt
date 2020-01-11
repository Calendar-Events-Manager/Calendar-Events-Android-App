package com.mymeetings.android.model

import com.mymeetings.android.db.CalendarEventsRepository

class CalendarEventsSyncManager(private val calendarEventsRepository: CalendarEventsRepository, private val calendarFetchStrategies : List<CalendarFetchStrategy>) {

    suspend fun getUpcomingMeetings(): List<CalendarEvent>{
        return calendarEventsRepository.getUpcomingMeetings()
    }

    suspend fun syncCloudCalendar() {
        calendarFetchStrategies.forEach {
            calendarEventsRepository.clearMeetingsData()
            calendarEventsRepository.addMeetings(it.fetchCalendarEvents())
        }
    }
}