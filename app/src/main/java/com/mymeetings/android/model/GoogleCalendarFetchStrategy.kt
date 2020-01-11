package com.mymeetings.android.model

class GoogleCalendarFetchStrategy :
    CalendarFetchStrategy {

    override suspend fun fetchCalendarEvents(): List<CalendarEvent> {
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
}