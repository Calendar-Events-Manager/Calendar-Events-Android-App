package com.mymeetings.android.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mymeetings.android.debug.ConsoleLog
import com.mymeetings.android.model.managers.CalendarEventsSyncManager
import com.mymeetings.android.model.strategies.CalendarFetchStrategyType
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class LocalCalendarChangeEventReceiver()
    : BroadcastReceiver(), KoinComponent {

    private val calendarEventsSyncManager: CalendarEventsSyncManager = get()

    override fun onReceive(context: Context?, intent: Intent?) {
        ConsoleLog.i(message = "Receiver received")
        calendarEventsSyncManager.fetchCalendarEvents(listOf(CalendarFetchStrategyType.LOCAL_CALENDAR))
    }
}