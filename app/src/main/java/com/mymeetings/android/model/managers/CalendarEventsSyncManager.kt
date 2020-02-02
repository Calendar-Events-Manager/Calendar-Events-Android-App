package com.mymeetings.android.model.managers

import androidx.lifecycle.MutableLiveData
import com.mymeetings.android.db.repositories.CalendarEventsRepository
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.strategies.CalendarFetchStrategy
import com.mymeetings.android.utils.ClockUtils
import java.util.concurrent.TimeUnit

class CalendarEventsSyncManager(
    private val calendarEventsRepository: CalendarEventsRepository,
    private val calendarFetchStrategy: CalendarFetchStrategy,
    private val clockUtils: ClockUtils
) {

    val calendarEventsLiveData = MutableLiveData<List<CalendarEvent>>()

    suspend fun getUpcomingCalendarEvents() {
        calendarEventsLiveData.postValue(calendarEventsRepository.getRelevantCalendarEvents())
    }

    suspend fun syncCalendarEvents() {
        val fetchUpTo = clockUtils.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)
        val fetchFrom = clockUtils.currentTimeMillis()
        calendarEventsRepository.clearCalendarEvents()
        val calendarEvents = calendarFetchStrategy.fetchCalendarEvents(fetchFrom, fetchUpTo)
        calendarEventsRepository.addCalendarEvents(calendarEvents)
        calendarEventsLiveData.postValue(calendarEvents)
    }
}