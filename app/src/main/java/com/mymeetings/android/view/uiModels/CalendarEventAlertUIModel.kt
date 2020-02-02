package com.mymeetings.android.view.uiModels

import com.mymeetings.android.ViewAlertType

data class CalendarEventAlertUIModel(
    val id : Long?,
    val title : String,
    val viewAlertType: ViewAlertType,
    val eventTimeLine : String,
    val timeLeft : String
)