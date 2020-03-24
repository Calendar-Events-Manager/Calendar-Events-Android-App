package com.mymeetings.android.strategies

import com.mymeetings.android.model.CalendarEvent

class GoogleCalendarFetchStrategy :
    CalendarFetchStrategy {

    override fun fetchCalendarEvents(fetchFrom : Long, fetchUpTo : Long): List<CalendarEvent> {
        if(isAuthorized()) {

        }

        return emptyList()
    }

    override fun isAuthorized(): Boolean {
        return false
    }

    override fun authorize(status: (Boolean) -> Unit) {
        status(false)
    }

    override fun getCalendarFetchStrategyType() =
        CalendarFetchStrategyType.GOOGLE_CALENDAR
}