package com.mymeetings.android.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mymeetings.android.db.MeetingsDBModel
import com.mymeetings.android.db.MeetingsDao
import java.util.concurrent.TimeUnit

class MeetingsMaintainer(private val meetingsDao: MeetingsDao) {

    fun addMeeting(meeting: Meeting) {
        meetingsDao.addMeeting(
            MeetingsDBModel(
                title = meeting.meetingTitle,
                time = meeting.time
            )
        )
    }

    fun updateMeeting(meeting: Meeting) {
        meetingsDao.updateMeeting(
            MeetingsDBModel(
                id = meeting.meetingId,
                title = meeting.meetingTitle,
                isDone = meeting.isDone,
                time = meeting.time
        ))
    }

    fun getUpcomingMeetings(): LiveData<List<Meeting>> {
        val latency = TimeUnit.MINUTES.toMillis(5)
        val listOfMeetingDBModel =
            meetingsDao.getUpComingMeetings(System.currentTimeMillis() - latency)

        return MutableLiveData<List<Meeting>>(listOfMeetingDBModel.map {
            Meeting(
                meetingTitle = it.title,
                time = it.time,
                isDone = it.isDone
            )
        })
    }


}