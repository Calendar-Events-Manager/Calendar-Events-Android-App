package com.mymeetings.android.db

import com.mymeetings.android.db.repositories.RoomCalendarEventsDataRepository
import com.mymeetings.android.model.CalendarEvent
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
    fun `getUpComingEvents should call Dao_getCalendarEvent with current time from clockUtils`() {
        val currentTimeMillis = System.currentTimeMillis()
        coEvery {
            clockUtils.currentTimeMillis()
        } returns currentTimeMillis

        runBlocking {
            calendarEventsDataRepository.getRelevantCalendarEvents()
        }

        coVerify { calendarEventsDao.getCalendarEventsEndingAfter(currentTimeMillis) }
    }

    @Test
    fun `addCalendarEvent should call Dao_addCalendarEvent()`() {

        val title = "ABC"
        val startTime = 1L
        val endTime = 2L

        runBlocking {
            calendarEventsDataRepository.addCalendarEvent(
                CalendarEvent(
                    title = title,
                    startTime = startTime,
                    endTime = endTime
                )
            )
        }

        coVerify {
            calendarEventsDao.addCalendarEvent(
                CalendarEventDbModel(
                    title = title,
                    startTime = startTime,
                    endTime = endTime
                )
            )
        }
    }

    @Test
    fun `addCalendarEvents should call Dao_addCalendarEvents()`() {

        val title = "ABC"
        val startTime = 1L
        val endTime = 2L

        runBlocking {
            calendarEventsDataRepository.addCalendarEvents(
                listOf(
                    CalendarEvent(
                        title = title,
                        startTime = startTime,
                        endTime = endTime
                    )
                )
            )
        }

        coVerify {
            calendarEventsDao.addCalendarEvents(
                listOf(
                    CalendarEventDbModel(
                        title = title,
                        startTime = startTime,
                        endTime = endTime
                    )
                )
            )
        }
    }


    @Test
    fun `updateCalendarEvent should call Dao_updateCalendarEvent()`() {

        val title = "ABC"
        val startTime = 1L
        val endTime = 2L

        runBlocking {
            calendarEventsDataRepository.updateCalendarEvents(
                CalendarEvent(
                    title = title,
                    startTime = startTime,
                    endTime = endTime
                )
            )
        }

        coVerify {
            calendarEventsDao.updateCalendarEvent(
                CalendarEventDbModel(
                    title = title,
                    startTime = startTime,
                    endTime = endTime
                )
            )
        }
    }

    @Test
    fun `clearCalendarEvents should call Dao_purgeCalendarEvent()`() {

        runBlocking {
            calendarEventsDataRepository.clearCalendarEvents()
        }

        coVerify {
            calendarEventsDao.purgeCalendarEvents()
        }
    }

}