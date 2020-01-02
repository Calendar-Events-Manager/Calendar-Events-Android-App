package com.mymeetings.android.model

class Meeting(
    val meetingId : Long? = null,
    var meetingTitle : String,
    var startTime : Long,
    var endTime : Long,
    var isDone : Boolean = false) {

    fun markAsDone() {
        isDone = true
    }
}