package com.mymeetings.android.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.util.*

class ClockUtilsTest {

    private val UTC_TIME_ZONE = TimeZone.getTimeZone("Europe/London")

    @Test
    fun `getFormattedTime should format time as hh mm a pattern for AM`() {
        val timeInMillis = 1579495210000
        val expectedFormattedTime = "10:10 AM" // for GMT +5.30

        val actualFormattedTime = ClockUtils().getFormattedTime(timeInMillis)

        assertThat(actualFormattedTime).isEqualTo(expectedFormattedTime)
    }

    @Test
    fun `getFormattedTime should format time as hh mm a pattern for PM`() {
        val timeInMillis = 1579530080000
        val expectedFormattedTime = "07:51 PM" // for GMT +5.30

        val actualFormattedTime = ClockUtils().getFormattedTime(timeInMillis)

        assertThat(actualFormattedTime).isEqualTo(expectedFormattedTime)
    }
}