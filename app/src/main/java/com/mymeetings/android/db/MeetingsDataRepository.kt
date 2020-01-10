package com.mymeetings.android.db

import com.mymeetings.android.model.Meeting

interface MeetingsDataRepository {

    suspend fun getUpComingMeetings(startTime: Long): List<Meeting>

    suspend fun addMeeting(meeting: Meeting)

    suspend fun addMeetings(meetings : List<Meeting>)

    suspend fun updateMeeting(meeting: Meeting)

    suspend fun clearMeetingsData()
}

class RoomMeetingDataRepository(private val meetingsDao: MeetingsDao) : MeetingsDataRepository {

    override suspend fun getUpComingMeetings(startTime: Long): List<Meeting> {
        return meetingsDao.getUpComingMeetings(startTime).map {
            Meeting(
                meetingId = it.id,
                meetingTitle = it.title,
                startTime = it.startTime,
                endTime = it.endTime,
                isDeleted = it.isDone
            )
        }
    }

    override suspend fun addMeeting(meeting: Meeting) {
        meetingsDao.addMeeting(
            MeetingsDBModel(
                title = meeting.meetingTitle,
                startTime = meeting.startTime,
                endTime = meeting.endTime,
                isDone = meeting.isDeleted
            )
        )
    }

    override suspend fun addMeetings(meetings: List<Meeting>) {
        meetingsDao.addMeetings(* meetings.map { meeting ->
            MeetingsDBModel(
                title = meeting.meetingTitle,
                startTime = meeting.startTime,
                endTime = meeting.endTime,
                isDone = meeting.isDeleted
            )
        }.toTypedArray())
    }

    override suspend fun updateMeeting(meeting: Meeting) {
        meetingsDao.updateMeeting(MeetingsDBModel(
            id = meeting.meetingId,
            title = meeting.meetingTitle,
            startTime = meeting.startTime,
            endTime = meeting.endTime,
            isDone = meeting.isDeleted
            ))
    }

    override suspend fun clearMeetingsData() {
        meetingsDao.purgeMeetings()
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

    override suspend fun addMeetings(meetings: List<Meeting>) {
        this.meetings.addAll(meetings)
    }

    override suspend fun updateMeeting(meeting: Meeting) {
        val meetingToUpdate = meetings.first {
            it.meetingId == meeting.meetingId
        }
        meetingToUpdate.meetingTitle = meeting.meetingTitle
        meetingToUpdate.isDeleted = meeting.isDeleted
        meetingToUpdate.startTime = meeting.startTime
        meetingToUpdate.endTime = meeting.endTime
    }

    override suspend fun clearMeetingsData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}