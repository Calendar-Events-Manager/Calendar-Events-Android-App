package com.mymeetings.android.db

import com.mymeetings.android.db.repositories.RoomCalendarEventsDataRepository
import com.mymeetings.android.utils.ClockUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RoomCalendarEventDataRepositoryTest {

    private lateinit var calendarEventsDataRepository: RoomCalendarEventsDataRepository

    @RelaxedMockK
    private lateinit var calendarEventsDao: CalendarEventsDao

    @RelaxedMockK
    private lateinit var clockUtils: ClockUtils

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        calendarEventsDataRepository =
            RoomCalendarEventsDataRepository(
                calendarEventsDao,
                clockUtils
            )

    }

    @Test
    fun shouldGetUpcomingMeetings() {
        val currentTimeMillis = System.currentTimeMillis()
        coEvery {
            clockUtils.currentTimeMillis()
        } returns currentTimeMillis

        runBlocking {
            calendarEventsDataRepository.getUpcomingCalendarEvents()
        }

        coVerify { calendarEventsDao.getCalendarEventsBy(currentTimeMillis) }
    }
}