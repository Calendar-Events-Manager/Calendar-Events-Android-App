package com.mymeetings.android.model.managers

import androidx.lifecycle.MutableLiveData
import com.mymeetings.android.db.repositories.CalendarEventsRepository
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.strategies.CalendarFetchStrategy
import com.mymeetings.android.model.strategies.CalendarFetchStrategyType
import com.mymeetings.android.utils.ClockUtils
import java.util.concurrent.TimeUnit

class CalendarEventsSyncManager(
    private val calendarEventsRepository: CalendarEventsRepository,
    private val calendarFetchStrategies: List<CalendarFetchStrategy>,
    private val clockUtils: ClockUtils
) {

    val calendarEventsLiveData = MutableLiveData<List<CalendarEvent>>()

    suspend fun getUpcomingCalendarEvents() {
        calendarEventsLiveData.postValue(calendarEventsRepository.getRelevantCalendarEvents())
    }

    suspend fun fetchCalendarEvents(
        calendarTypesToFetch: List<CalendarFetchStrategyType> = listOf(
            CalendarFetchStrategyType.LOCAL_CALENDAR,
            CalendarFetchStrategyType.GOOGLE_CALENDAR
        )
    ) {
        val fetchUpTo = clockUtils.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)
        val fetchFrom = clockUtils.currentTimeMillis()
        calendarEventsRepository.clearCalendarEvents()
        calendarFetchStrategies.forEach {
            if (calendarTypesToFetch.contains(it.getCalendarFetchStrategyType())) {
                val calendarEvents = it.fetchCalendarEvents(
                    fetchFrom,
                    fetchUpTo
                )
                calendarEventsRepository.addCalendarEvents(
                    calendarEvents
                )
            }
        }

        getUpcomingCalendarEvents()
    }
}