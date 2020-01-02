package com.mymeetings.android.di

import android.content.Context
import com.mymeetings.android.db.MeetingsDao
import com.mymeetings.android.db.MeetingsDataRepository
import com.mymeetings.android.db.MyMeetingsDatabase
import com.mymeetings.android.db.RoomMeetingDataRepository
import com.mymeetings.android.model.GoogleCalendarSync
import com.mymeetings.android.model.MeetingsMaintainer
import org.koin.dsl.module.module

class DIProvider(private val context: Context) {

    val module = module {

        single { MyMeetingsDatabase.getDb(context) }

        single {
            val myMeetingsDatabase = get<MyMeetingsDatabase>()
            myMeetingsDatabase.getMeetingsDao()
        }

        single {
            val meetingsDao = get<MeetingsDao>()
            RoomMeetingDataRepository(meetingsDao)
        }

        single {
            val meetingDataRepository = get<MeetingsDataRepository>()
            MeetingsMaintainer(meetingDataRepository, listOf(GoogleCalendarSync()))
        }
    }
}