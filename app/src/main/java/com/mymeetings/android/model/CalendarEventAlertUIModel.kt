package com.mymeetings.android.model

data class CalendarEventAlertUIModel(
    val id : Long?,
    val title : String,
    val viewAlertType: ViewAlertType,
    val eventTimeLine : String,
    val timeLeft : String
)