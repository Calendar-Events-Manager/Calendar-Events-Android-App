package com.mymeetings.android.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ClockUtilsTest {

    @Test
    fun `should get current time in milliseconds`() {
        val actual = ClockUtils().currentTimeMillis()
        val expected = System.currentTimeMillis()

        assertThat(actual).isAtMost(expected)
    }

    @Test
    fun `getFormattedTime should format time as hh mm a pattern`() {
        val timeInMillis = 1579530080000 //time of 07:51 for GMT +5.30

        assertThat(ClockUtils().getFormattedTime(timeInMillis)).isEqualTo("07:51 PM")
    }
}