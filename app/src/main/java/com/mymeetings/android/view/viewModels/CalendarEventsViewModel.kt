package com.mymeetings.android.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mymeetings.android.db.repositories.CalendarEventsRepository
import com.mymeetings.android.managers.CalendarEventsSyncManager
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.CalendarEventAlertUIModel
import com.mymeetings.android.model.extensions.toUiModelCollection
import com.mymeetings.android.utils.ClockUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarEventsViewModel(
    private val calendarEventsSyncManager: CalendarEventsSyncManager,
    private val calendarEventsRepository: CalendarEventsRepository
) : ViewModel() {

    suspend fun getCalendarEvents(): List<CalendarEvent> =
        calendarEventsRepository.getRelevantCalendarEvents()

    fun syncEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            calendarEventsSyncManager.syncCalendarEvents()
        }
    }
}