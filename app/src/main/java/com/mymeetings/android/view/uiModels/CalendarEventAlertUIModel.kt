package com.mymeetings.android.view.uiModels

import com.mymeetings.android.model.strategies.ViewAlertType

data class CalendarEventAlertUIModel(
    val id : Long?,
    val title : String,
    val viewAlertType: ViewAlertType,
    val eventTimeLine : String,
    val timeLeft : String
)