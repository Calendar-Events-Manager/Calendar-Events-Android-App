package com.mymeetings.android.db

import androidx.room.*

@Dao
interface CalendarEventsDao {

    @Query("SELECT * FROM calendar_events where end_time > :givenTime ORDER BY start_time ASC LIMIT 100")
    fun getMeetingsBy(givenTime : Long): List<CalendarEventsDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeeting(calendarEventsDbModel: CalendarEventsDbModel): Long

    @Update
    fun updateMeeting(calendarEventsDbModel: CalendarEventsDbModel) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeetings(vararg meetingDBModel : CalendarEventsDbModel) : LongArray

    @Query("DELETE FROM calendar_events")
    fun purgeMeetings()
}