package com.mymeetings.android.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class ClockUtilsTest {

    @Test
    fun `should get current time in milliseconds`() {
        val delta = 1 //Since System.currentTimeInMillis will be executed little before than ClockUtils.
        val actual = ClockUtils().currentTimeMillis()
        val expected = System.currentTimeMillis()

        assertTrue(expected - actual < delta)
    }

    @Test
    fun `getFormattedTime should format time as hh mm a pattern`() {
        val timeInMillis = 1579530080000 //time of 07:51 for GMT +5.30

        assertEquals("07:51 PM", ClockUtils().getFormattedTime(timeInMillis))
    }
}