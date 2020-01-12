package com.mymeetings.android.db

import androidx.room.*

@Dao
interface CalendarEventsDao {

    @Query("SELECT * FROM calendar_events where end_time > :givenTime ORDER BY start_time ASC LIMIT 100")
    fun getCalendarEventsEndingAfter(givenTime : Long): List<CalendarEventsDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCalendarEvent(calendarEvents: CalendarEventsDbModel): Long

    @Update
    fun updateCalendarEvent(calendarEvents: CalendarEventsDbModel) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCalendarEvents(calendarEvents : List<CalendarEventsDbModel>) : LongArray

    @Query("DELETE FROM calendar_events")
    fun purgeCalendarEvents()
}