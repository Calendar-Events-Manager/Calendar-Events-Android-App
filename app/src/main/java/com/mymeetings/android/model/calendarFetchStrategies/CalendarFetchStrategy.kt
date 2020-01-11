package com.mymeetings.android.model.calendarFetchStrategies

import com.mymeetings.android.model.CalendarEvent

interface CalendarFetchStrategy {

    suspend fun fetchCalendarEvents(fetchFrom : Long, fetchUpTo : Long) : List<CalendarEvent>

    fun isAuthorized() : Boolean

    fun authorize(status : (Boolean) -> Unit)

    fun getCalendarFetchStrategyType() : CalendarFetchStrategyType
}

enum class CalendarFetchStrategyType {
    LOCAL_CALENDAR,
    GOOGLE_CALENDAR
}

