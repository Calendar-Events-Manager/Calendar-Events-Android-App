package com.mymeetings.android.model.extensions

import com.google.common.truth.Truth.assertThat
import com.mymeetings.android.db.CalendarEventDbModel
import com.mymeetings.android.model.CalendarEvent
import org.junit.Test

class MapperExtensionsKtTest {

    @Test
    fun `toDbModel should map CalendarEvent to CalendarEventsDbModel`() {
        val id = 1L
        val title = "ABC"
        val startTime = 1L
        val endTime = 2L
        val isDeleted = false

        val calendarEvent = CalendarEvent(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            isDeleted = isDeleted
        )

        val expectedCalendarEventDb = CalendarEventDbModel(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            isDone = isDeleted
        )

        val actualCalendarEventDb = calendarEvent.toDbModel()

        assertThat(actualCalendarEventDb).isEqualTo(expectedCalendarEventDb)
    }

    @Test
    fun `toDomainModel should map CalendarEventsDbModel to CalendarEvent`() {
        val id = 1L
        val title = "ABC"
        val startTime = 1L
        val endTime = 2L
        val isDeleted = false

        val calendarEventDb = CalendarEventDbModel(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            isDone = isDeleted
        )

        val expectedCalendarEvent = CalendarEvent(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            isDeleted = isDeleted
        )

        val actualCalendarEvent = calendarEventDb.toDomainModel()

        assertThat(actualCalendarEvent).isEqualTo(expectedCalendarEvent)
    }
}