package com.mymeetings.android.model

import com.mymeetings.android.db.MeetingsDataRepository
import java.util.concurrent.TimeUnit

class MeetingsMaintainer(private val meetingsDataRepository: MeetingsDataRepository, private val cloudCalendarSyncs : List<CloudCalendarSync>) {

    suspend fun getUpcomingMeetings(): List<Meeting>{
        val latency = TimeUnit.MINUTES.toMillis(5)

        return meetingsDataRepository.getUpComingMeetings(System.currentTimeMillis() - latency)
    }

    suspend fun syncCloudCalendar() {
        cloudCalendarSyncs.forEach {
            val meetings = it.getMeetingsFromCloud()
            meetingsDataRepository.clearMeetingsData()
            meetingsDataRepository.addMeetings(meetings)
        }
    }
}