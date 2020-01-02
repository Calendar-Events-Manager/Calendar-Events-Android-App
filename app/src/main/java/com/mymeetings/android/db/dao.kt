package com.mymeetings.android.db

import androidx.room.*

@Dao
interface MeetingsDao {

    @Query("SELECT * FROM meetings where time > :startTime ORDER BY time ASC LIMIT 100")
    fun getUpComingMeetings(startTime : Long): List<MeetingsDBModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeeting(meetingsDBModel: MeetingsDBModel): Long

    @Update
    fun updateMeeting(meetingsDBModel: MeetingsDBModel) : Int
}