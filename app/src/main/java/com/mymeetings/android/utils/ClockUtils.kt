package com.mymeetings.android.utils

import android.text.format.DateUtils
import android.util.TimeUtils
import java.util.*


class ClockUtils {
    fun currentTimeMillis() = System.currentTimeMillis()

    fun getTimeLeft(timeInMillis : Long) = DateUtils.getRelativeTimeSpanString(timeInMillis)
}