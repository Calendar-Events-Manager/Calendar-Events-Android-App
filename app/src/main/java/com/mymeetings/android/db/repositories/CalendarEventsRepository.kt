package com.mymeetings.android.db.repositories

import com.mymeetings.android.model.CalendarEvent

interface CalendarEventsRepository {

    suspend fun getUpcomingCalendarEvents(): List<CalendarEvent>

    suspend fun addCalendarEvent(calendarEvent: CalendarEvent)

    suspend fun addCalendarEvents(calendarEvents : List<CalendarEvent>)

    suspend fun updateCalendarEvents(calendarEvent: CalendarEvent)

    suspend fun clearCalendarEvents()
}

