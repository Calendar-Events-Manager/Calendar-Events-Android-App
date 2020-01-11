package com.mymeetings.android.model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.CalendarContract
import androidx.core.content.ContextCompat

class LocalCalendarFetchStrategy(private val context: Context) :
    CalendarFetchStrategy {

    @SuppressLint("MissingPermission")
    override suspend fun fetchCalendarEvents(): List<CalendarEvent> {

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
            // Use the cursor to step through the returned records
            while (calCur?.moveToNext() == true) {
                // Get the field values
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
                    null,
                    null,
                    null
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

}