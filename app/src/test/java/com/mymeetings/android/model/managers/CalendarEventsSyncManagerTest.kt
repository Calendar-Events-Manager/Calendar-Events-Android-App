package com.mymeetings.android.model.managers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mymeetings.android.db.repositories.CalendarEventsRepository
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.strategies.CalendarFetchStrategy
import com.mymeetings.android.model.strategies.CalendarFetchStrategyType
import com.mymeetings.android.utils.ClockUtils
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.TimeUnit


class CalendarEventsSyncManagerTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var calendarEventsRepository: CalendarEventsRepository

    @MockK
    private lateinit var clockUtils: ClockUtils

    @RelaxedMockK
    private lateinit var calendarFetchStrategy: CalendarFetchStrategy

    private lateinit var calendarEventsSyncManager: CalendarEventsSyncManager

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        calendarEventsSyncManager = CalendarEventsSyncManager(
            calendarEventsRepository,
            listOf(calendarFetchStrategy),
            clockUtils
        )
    }

    @Test
    fun `getUpComingCalendarEvents should get events and post liveData`() {

        val observer: Observer<List<CalendarEvent>> = mockk(relaxed = true)
        val calendarEvents = listOf(CalendarEvent(title = "BlaBla", startTime = 0L, endTime = 0L))

        coEvery { calendarEventsRepository.getRelevantCalendarEvents() } returns calendarEvents
        calendarEventsSyncManager.calendarEventsLiveData.observeForever(observer)

        runBlocking {
            calendarEventsSyncManager.getUpcomingCalendarEvents()
        }

        verify { observer.onChanged(calendarEvents) }
    }

    @Test
    fun `fetchCalendarEvents should clear the repository and add the calendar events fetched from fetchStrategies for a day`() {
        val currentTimeInMillis = 5L
        val calendarEvents = listOf(CalendarEvent(title = "BlaBla", startTime = 0L, endTime = 0L))

        every { clockUtils.currentTimeMillis() } returns currentTimeInMillis
        coEvery {
            calendarFetchStrategy.fetchCalendarEvents(
                currentTimeInMillis,
                currentTimeInMillis + TimeUnit.DAYS.toMillis(1)
            )
        } returns calendarEvents
        coEvery {
            calendarFetchStrategy.getCalendarFetchStrategyType()
        } returns CalendarFetchStrategyType.LOCAL_CALENDAR

        runBlocking {
            calendarEventsSyncManager.fetchCalendarEvents()
        }

        coVerifyOrder {
            calendarEventsRepository.clearCalendarEvents()
            calendarEventsRepository.addCalendarEvents(calendarEvents)
        }
    }
}