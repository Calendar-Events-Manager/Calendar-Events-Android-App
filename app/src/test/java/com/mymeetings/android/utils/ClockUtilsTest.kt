package com.mymeetings.android.utils

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.spyk
import org.junit.Test
import java.util.*

class ClockUtilsTest {

    private val clockUtils = spyk(ClockUtils())

    @Test
    fun `getFormattedTime should format time as hh mm a pattern for AM`() {
        val expectedFormattedTime = "10:10 AM"
        val timeInMillis = 1579495210000
        every {
            clockUtils.getDate(timeInMillis)
        } returns Date(2020, 1, 1, 10, 10)


        val actualFormattedTime = clockUtils.getFormattedTime(timeInMillis)

        assertThat(actualFormattedTime).isEqualTo(expectedFormattedTime)
    }

    @Test
    fun `getFormattedTime should format time as hh mm a pattern for PM`() {
        val expectedFormattedTime = "07:50 PM"
        val timeInMillis = 1579530080000
        every {
            clockUtils.getDate(timeInMillis)
        } returns Date(2020, 1, 1, 19, 50)


        val actualFormattedTime = clockUtils.getFormattedTime(timeInMillis)

        assertThat(actualFormattedTime).isEqualTo(expectedFormattedTime)
    }
}