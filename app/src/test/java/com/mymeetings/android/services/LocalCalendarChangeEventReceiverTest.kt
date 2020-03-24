package com.mymeetings.android.services

import android.content.Context
import android.content.Intent
import com.mymeetings.android.debug.ConsoleLog
import com.mymeetings.android.managers.CalendarEventsSyncManager
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
class LocalCalendarChangeEventReceiverTest : KoinTest {

    private lateinit var localCalendarChangeEventReceiver: LocalCalendarChangeEventReceiver

    @RelaxedMockK
    lateinit var calendarEventsSyncManager: CalendarEventsSyncManager

    @RelaxedMockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var consoleLog: ConsoleLog

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        loadKoinModules(module {
            single {
                calendarEventsSyncManager
            }
            single {
                consoleLog
            }
        })

        localCalendarChangeEventReceiver = LocalCalendarChangeEventReceiver()
    }

    @Test
    fun onReceiveShouldCallCalendarEventSyncManagerToFetchLocalCalendarEvents() = runBlockingTest {

        localCalendarChangeEventReceiver.onReceive(context, Intent())

        coVerify {
            calendarEventsSyncManager.syncCalendarEvents()
        }
    }
}