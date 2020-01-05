package com.mymeetings.android.model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import androidx.core.content.ContextCompat

interface CloudCalendarSync {

    suspend fun getMeetingsFromCloud() : List<Meeting>

    fun isAuthorized() : Boolean

    fun authorize(status : (Boolean) -> Unit)
}

class GoogleCalendarSync : CloudCalendarSync {

    override suspend fun getMeetingsFromCloud(): List<Meeting> {
        if(isAuthorized()) {

        }

        return emptyList()
    }

    override fun isAuthorized(): Boolean {
        return false
    }

    override fun authorize(status: (Boolean) -> Unit) {
        status(false)
    }
}

class LocalCalendarSync(private val context: Context) : CloudCalendarSync {

    @SuppressLint("MissingPermission")
    override suspend fun getMeetingsFromCloud(): List<Meeting> {

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

            while (eventCur?.moveToNext() == true) {
                val title = eventCur.getString(1)
                println(title)
                Log.v("XDFCE", title)
            }

            eventCur?.close()
        }

        return emptyList()
    }

    override fun isAuthorized(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
    }

    override fun authorize(status: (Boolean) -> Unit) {
        if(isAuthorized()) {
            status(true)
        }
    }

}