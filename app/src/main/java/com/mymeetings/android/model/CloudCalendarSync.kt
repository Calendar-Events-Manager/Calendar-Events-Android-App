package com.mymeetings.android.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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

    override suspend fun getMeetingsFromCloud(): List<Meeting> {
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