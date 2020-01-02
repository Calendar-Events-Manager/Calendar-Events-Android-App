package com.mymeetings.android.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mymeetings.android.db.MeetingsDataRepository
import java.util.concurrent.TimeUnit

class MeetingsMaintainer(private val meetingsDataRepository: MeetingsDataRepository, private val cloudCalendarSyncs : List<CloudCalendarSync>) {

    suspend fun addMeeting(meeting: Meeting) {
        meetingsDataRepository.addMeeting(meeting)
    }

    suspend fun updateMeeting(meeting: Meeting) {
        meetingsDataRepository.updateMeeting(meeting)
    }

    suspend fun getUpcomingMeetings(): LiveData<List<Meeting>> {
        val latency = TimeUnit.MINUTES.toMillis(5)
        val meetings = meetingsDataRepository.getUpComingMeetings(System.currentTimeMillis() - latency)

        return MutableLiveData<List<Meeting>>(meetings)
    }

    suspend fun syncCloudCalendar() {

    }
}