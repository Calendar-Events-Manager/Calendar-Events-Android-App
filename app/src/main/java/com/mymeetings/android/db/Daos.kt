package com.mymeetings.android.db

import androidx.room.*

@Dao
interface CalendarEventsDao {

    @Query("SELECT * FROM calendar_events where end_time > :givenTime ORDER BY start_time ASC LIMIT 100")
    fun getCalendarEventsEndingAfter(givenTime : Long): List<CalendarEventDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCalendarEvent(calendarEvent: CalendarEventDbModel): Long

    @Update
    fun updateCalendarEvent(calendarEvent: CalendarEventDbModel) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCalendarEvents(calendarEvents : List<CalendarEventDbModel>) : LongArray

    @Query("DELETE FROM calendar_events")
    fun purgeCalendarEvents()
}