package com.mymeetings.android.model.strategies

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import com.mymeetings.android.model.CalendarEvent

class LocalCalendarFetchStrategy(private val context: Context) : CalendarFetchStrategy {

    @SuppressLint("MissingPermission")
    override suspend fun fetchCalendarEvents(fetchFrom : Long, fetchUpTo : Long): List<CalendarEvent> {

        if(isAuthorized()) {
            val calCur: Cursor? =
                context.contentResolver.query(
                    CalendarContract.Calendars.CONTENT_URI,
                    arrayOf(CalendarContract.Calendars._ID),
                    null,
                    null,
                    null
                )

            val calIds = mutableListOf<Long>()
            while (calCur?.moveToNext() == true) {
                val calID: Long = calCur.getLong(0)
                calIds.add(calID)
            }

            calCur?.close()


            val eventCur : Cursor? =
                context.contentResolver.query(
                    CalendarContract.Events.CONTENT_URI,
                    arrayOf(
                        CalendarContract.Events._ID,
                        CalendarContract.Events.TITLE,
                        CalendarContract.Events.DTSTART,
                        CalendarContract.Events.DTEND
                    ),
                    "${CalendarContract.Events.DTEND} >= ? AND ${CalendarContract.Events.DTSTART} <= ?",
                    arrayOf(fetchFrom.toString(), fetchUpTo.toString()),
                    "${CalendarContract.Events.DTSTART} ASC"
                )

            val meetings = mutableListOf<CalendarEvent>()
            while (eventCur?.moveToNext() == true) {
                val title = eventCur.getString(1)
                val startTime = eventCur.getLong(2)
                val endTime = eventCur.getLong(3)

                meetings.add(
                    CalendarEvent(
                        title = title,
                        startTime = startTime,
                        endTime = endTime
                    )
                )
            }

            eventCur?.close()

            return meetings
        }

        return emptyList()
    }

    override fun isAuthorized(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CALENDAR
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun authorize(status: (Boolean) -> Unit) {
        if(isAuthorized()) {
            status(true)
        }
    }

    override fun getCalendarFetchStrategyType() = CalendarFetchStrategyType.LOCAL_CALENDAR

}