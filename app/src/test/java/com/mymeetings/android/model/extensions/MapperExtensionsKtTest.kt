package com.mymeetings.android.model.extensions

import com.google.common.truth.Truth.assertThat
import com.mymeetings.android.db.CalendarEventsDbModel
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.CalendarEventPriority
import org.junit.Test

class MapperExtensionsKtTest {

    @Test
    fun `toDbModel should map CalendarEvent to CalendarEventsDbModel`() {
        val id = 1L
        val title = "ABC"
        val startTime = 1L
        val endTime = 2L
        val priority = CalendarEventPriority.MEDIUM
        val isDeleted = false

        val calendarEvent = CalendarEvent(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            priority = priority,
            isDeleted = isDeleted
        )

        val expectedData = CalendarEventsDbModel(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            isDone = isDeleted
        )

        assertThat(calendarEvent.toDbModel()).isEqualTo(expectedData)
    }

    @Test
    fun `toDomainModel should map CalendarEventsDbModel to CalendarEvent`() {
        val id = 1L
        val title = "ABC"
        val startTime = 1L
        val endTime = 2L
        val isDeleted = false

        val calendarEvent = CalendarEventsDbModel(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            isDone = isDeleted
        )

        val expected = CalendarEvent(
            id = id,
            title = title,
            startTime = startTime,
            endTime = endTime,
            isDeleted = isDeleted
        )
        val domainModel = calendarEvent.toDomainModel()

        assertThat(domainModel).isEqualTo(expected)
    }
}