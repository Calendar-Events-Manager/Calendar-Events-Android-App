package com.mymeetings.android.view.activities.ui.home

import androidx.lifecycle.ViewModel
import com.mymeetings.android.model.CalendarEventsSyncManager
import kotlinx.coroutines.*

class CalendarEventsViewModel(
    private val calendarEventsSyncManager: CalendarEventsSyncManager) : ViewModel() {

    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    fun getCalendarEventLiveData() = calendarEventsSyncManager.calendarEventsLiveData

    fun getEvents() {
        backgroundScope.launch {
            calendarEventsSyncManager.getUpcomingCalendarEvents()
        }
    }

    fun syncEvents() {
        backgroundScope.launch {
            calendarEventsSyncManager.fetchCalendarEvents()
        }
    }
}