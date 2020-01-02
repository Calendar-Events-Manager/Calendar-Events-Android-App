package com.mymeetings.android.db

import com.mymeetings.android.model.Meeting

interface MeetingsDataRepository {

    suspend fun getUpComingMeetings(startTime: Long): List<Meeting>

    suspend fun addMeeting(meeting: Meeting)

    suspend fun updateMeeting(meeting: Meeting)
}

class RoomMeetingDataRepository(private val meetingsDao: MeetingsDao) : MeetingsDataRepository {

    override suspend fun getUpComingMeetings(startTime: Long): List<Meeting> {
        return meetingsDao.getUpComingMeetings(startTime).map {
            Meeting(
                meetingId = it.id,
                meetingTitle = it.title,
                startTime = it.startTime,
                endTime = it.endTime,
                isDone = it.isDone
            )
        }
    }

    override suspend fun addMeeting(meeting: Meeting) {
        meetingsDao.addMeeting(
            MeetingsDBModel(
                title = meeting.meetingTitle,
                startTime = meeting.startTime,
                endTime = meeting.endTime,
                isDone = meeting.isDone
            )
        )
    }

    override suspend fun updateMeeting(meeting: Meeting) {
        meetingsDao.updateMeeting(MeetingsDBModel(
            id = meeting.meetingId,
            title = meeting.meetingTitle,
            startTime = meeting.startTime,
            endTime = meeting.endTime,
            isDone = meeting.isDone
            ))
    }
}

class PlainMeetingDataRepository() : MeetingsDataRepository {

    private val meetings = mutableListOf<Meeting>()

    override suspend fun getUpComingMeetings(startTime: Long): List<Meeting> {
        return meetings
    }

    override suspend fun addMeeting(meeting: Meeting) {
        meetings.add(meeting)
    }

    override suspend fun updateMeeting(meeting: Meeting) {
        val meetingToUpdate = meetings.first {
            it.meetingId == meeting.meetingId
        }
        meetingToUpdate.meetingTitle = meeting.meetingTitle
        meetingToUpdate.isDone = meeting.isDone
        meetingToUpdate.startTime = meeting.startTime
        meetingToUpdate.endTime = meeting.endTime
    }

}