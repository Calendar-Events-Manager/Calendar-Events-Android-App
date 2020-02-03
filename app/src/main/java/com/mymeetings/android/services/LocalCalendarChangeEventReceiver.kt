package com.mymeetings.android.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mymeetings.android.debug.ConsoleLog
import com.mymeetings.android.managers.CalendarEventsSyncManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class LocalCalendarChangeEventReceiver
    : BroadcastReceiver(), KoinComponent {

    private val calendarEventsSyncManager: CalendarEventsSyncManager = get()
    private val consoleLog: ConsoleLog = get()

    override fun onReceive(context: Context?, intent: Intent?) {
        consoleLog.i(tag = "LocalCalendarChangeEventReceiver", message = "onReceive called")
        CoroutineScope(Dispatchers.IO).launch {
            calendarEventsSyncManager.syncCalendarEvents()
        }
    }
}