package com.mymeetings.android.view.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.managers.CalendarEventAlertManager
import com.mymeetings.android.managers.CalendarEventsSyncManager
import com.mymeetings.android.utils.ClockUtils
import com.mymeetings.android.view.uiModels.CalendarEventAlertUIModel
import kotlinx.coroutines.*

class CalendarEventsViewModel(
    private val calendarEventsSyncManager: CalendarEventsSyncManager,
    private val calendarEventAlertManager: CalendarEventAlertManager,
    private val clockUtils: ClockUtils
) : ViewModel() {

    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    val calendarEventAlertsLiveData = MutableLiveData<List<CalendarEventAlertUIModel>>()

    private val observer = Observer<List<CalendarEvent>> { calendarEvents ->
        calendarEventAlertsLiveData.postValue(calendarEvents.map { calendarEvent ->
            computeCalendarEventUIModel(calendarEvent)
        })
    }

    init {
        calendarEventsSyncManager.calendarEventsLiveData.observeForever(observer)
    }

    private fun computeCalendarEventUIModel(calendarEvent: CalendarEvent): CalendarEventAlertUIModel {
        val calendarEventWithAlert = calendarEventAlertManager.getCalendarEventAlert(calendarEvent)
        val startTimeToEndTimeString = computeEventStartToEndTime(calendarEvent)
        val timeLeft = clockUtils.getTimeLeft(calendarEvent.startTime)

        return CalendarEventAlertUIModel(
            id = calendarEvent.id,
            title = calendarEvent.title,
            viewAlertType = calendarEventWithAlert.viewAlertType(clockUtils.currentTimeMillis()),
            eventTimeLine = startTimeToEndTimeString,
            timeLeft = timeLeft
        )
    }

    private fun computeEventStartToEndTime(calendarEvent: CalendarEvent): String {
        return "${clockUtils.getFormattedTime(calendarEvent.startTime)} - ${clockUtils.getFormattedTime(
            calendarEvent.endTime
        )}"
    }

    fun getEvents() {
        backgroundScope.launch {
            calendarEventsSyncManager.getUpcomingCalendarEvents()
        }
    }

    fun syncEvents() {
        backgroundScope.launch {
            calendarEventsSyncManager.syncCalendarEvents()
        }
    }

    override fun onCleared() {
        super.onCleared()
        calendarEventsSyncManager.calendarEventsLiveData.removeObserver(observer)
    }
}