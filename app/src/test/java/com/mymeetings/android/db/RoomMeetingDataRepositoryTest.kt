package com.mymeetings.android.db

import com.mymeetings.android.utils.ClockUtils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RoomMeetingDataRepositoryTest {

    private lateinit var meetingDataRepository: RoomMeetingDataRepository

    @RelaxedMockK
    private lateinit var meetingsDao: MeetingsDao

    @RelaxedMockK
    private lateinit var clockUtils: ClockUtils

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        meetingDataRepository = RoomMeetingDataRepository(meetingsDao, clockUtils)

    }

    @Test
    fun shouldGetUpcomingMeetings() {
        val currentTimeMillis = System.currentTimeMillis()
        coEvery {
            clockUtils.currentTimeMillis()
        } returns currentTimeMillis

        runBlocking {
            meetingDataRepository.getUpcomingMeetings()
        }

        coVerify { meetingsDao.getMeetingsBy(currentTimeMillis) }
    }
}