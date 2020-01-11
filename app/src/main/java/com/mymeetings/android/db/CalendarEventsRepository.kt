package com.mymeetings.android.db

import com.mymeetings.android.model.CalendarEvent

interface CalendarEventsRepository {

    suspend fun getUpcomingMeetings(): List<CalendarEvent>

    suspend fun addMeeting(calendarEvent: CalendarEvent)

    suspend fun addMeetings(calendarEvents : List<CalendarEvent>)

    suspend fun updateMeeting(calendarEvent: CalendarEvent)

    suspend fun clearMeetingsData()
}

