package com.mymeetings.android.model

data class CalendarEvent(
    val id : Long? = null,
    var title : String,
    var startTime : Long,
    var endTime : Long,
    var isDeleted : Boolean = false)