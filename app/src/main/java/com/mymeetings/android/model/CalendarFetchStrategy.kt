package com.mymeetings.android.model

interface CalendarFetchStrategy {

    suspend fun fetchCalendarEvents() : List<CalendarEvent>

    fun isAuthorized() : Boolean

    fun authorize(status : (Boolean) -> Unit)
}

