package com.mymeetings.android.model

import com.mymeetings.android.model.strategies.ViewAlertType
import com.mymeetings.android.utils.ClockUtils

class EventViewAlert(
    timeToShowItAsImmediateEvent: Long?,
    timeToShowItAsRunningEvent: Long,
    clockUtils: ClockUtils
) {

    val viewAlertType = when {
        clockUtils.currentTimeMillis() <= timeToShowItAsRunningEvent -> ViewAlertType.RUNNING
        timeToShowItAsImmediateEvent != null && clockUtils.currentTimeMillis() <= timeToShowItAsImmediateEvent -> ViewAlertType.PRIORITY
        else -> ViewAlertType.LOW
    }
}