package com.mymeetings.android.model

import androidx.lifecycle.MutableLiveData
import com.mymeetings.android.db.repositories.CalendarEventsRepository
import com.mymeetings.android.model.calendarFetchStrategies.CalendarFetchStrategy
import com.mymeetings.android.model.calendarFetchStrategies.CalendarFetchStrategyType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarEventsSyncManager(
    private val calendarEventsRepository: CalendarEventsRepository,
    private val calendarFetchStrategies: List<CalendarFetchStrategy>
) {

    val calendarEventsLiveData = MutableLiveData<List<CalendarEvent>>()

    fun getUpcomingCalendarEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            val upComingEvents = calendarEventsRepository.getUpcomingCalendarEvents()
            calendarEventsLiveData.postValue(upComingEvents)
        }
    }

    fun fetchCalendarEvents(calendarFetchStrategyType: CalendarFetchStrategyType? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            calendarEventsRepository.clearCalendarEvents()
            calendarFetchStrategies.forEach {
                if (calendarFetchStrategyType == null
                    || calendarFetchStrategyType == it.getCalendarFetchStrategyType()
                ) {
                    calendarEventsRepository.addCalendarEvents(it.fetchCalendarEvents())
                    getUpcomingCalendarEvents()
                }
            }
        }
    }
}