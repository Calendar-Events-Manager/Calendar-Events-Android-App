package com.mymeetings.android.view.activities.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.CalendarEventsSyncManager
import kotlinx.coroutines.*

class MeetingsViewModel(
    private val calendarEventsSyncManager: CalendarEventsSyncManager,
    val meetingsLiveData: MutableLiveData<List<CalendarEvent>>) : ViewModel() {

    companion object {
        const val MEETINGS_LIST_VIEW = "meetings_list_view"
    }

    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    fun getEvents() {
        backgroundScope.launch {
            val meetings = calendarEventsSyncManager.getUpcomingMeetings()
            meetingsLiveData.postValue(meetings)
        }
    }

    fun syncEvents() {
        backgroundScope.launch {
            calendarEventsSyncManager.syncCloudCalendar()
            meetingsLiveData.postValue(calendarEventsSyncManager.getUpcomingMeetings())
        }
    }
}