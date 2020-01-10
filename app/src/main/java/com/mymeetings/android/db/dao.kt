package com.mymeetings.android.db

import androidx.room.*

@Dao
interface MeetingsDao {

    @Query("SELECT * FROM meetings where end_time > :startTime ORDER BY start_time ASC LIMIT 100")
    fun getUpComingMeetings(startTime : Long): List<MeetingsDBModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeeting(meetingsDBModel: MeetingsDBModel): Long

    @Update
    fun updateMeeting(meetingsDBModel: MeetingsDBModel) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeetings(vararg meetingDBModel : MeetingsDBModel) : LongArray

    @Query("DELETE FROM meetings")
    fun purgeMeetings()
}