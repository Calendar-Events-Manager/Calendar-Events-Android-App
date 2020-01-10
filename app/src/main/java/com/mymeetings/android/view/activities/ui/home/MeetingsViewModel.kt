package com.mymeetings.android.view.activities.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mymeetings.android.model.Meeting
import com.mymeetings.android.model.MeetingsMaintainer
import kotlinx.coroutines.*

class MeetingsViewModel(
    private val meetingsMaintainer: MeetingsMaintainer,
    val meetingsLiveData: MutableLiveData<List<Meeting>>) : ViewModel() {

    companion object {
        const val MEETINGS_LIST_VIEW = "meetings_list_view"
    }

    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    fun getEvents() {
        backgroundScope.launch {
            val meetings = meetingsMaintainer.getUpcomingMeetings()
            meetingsLiveData.postValue(meetings)
        }
    }

    fun syncEvents() {
        backgroundScope.launch {
            meetingsMaintainer.syncCloudCalendar()
            meetingsLiveData.postValue(meetingsMaintainer.getUpcomingMeetings())
        }
    }
}