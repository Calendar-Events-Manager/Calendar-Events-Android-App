package com.mymeetings.android.model

class Meeting(
    val meetingId : Long? = null,
    var meetingTitle : String,
    var time : Long,
    var isDone : Boolean = false) {

    fun markAsDone() {
        isDone = true
    }
}