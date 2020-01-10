package com.mymeetings.android.di

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mymeetings.android.db.MeetingsDao
import com.mymeetings.android.db.MyMeetingsDatabase
import com.mymeetings.android.db.RoomMeetingDataRepository
import com.mymeetings.android.model.*
import com.mymeetings.android.utils.ClockUtils
import com.mymeetings.android.view.activities.ui.home.MeetingsViewModel
import com.mymeetings.android.view.widgets.MeetingWidgetRemoteViewFactory
import org.koin.android.viewmodel.ext.koin.viewModel
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
            RoomMeetingDataRepository(meetingsDao, get())
        }

        single { ClockUtils() }

        single(name = "meetingsLiveData") { MutableLiveData<List<Meeting>>() }

        single {
            val meetingDataRepository = get<RoomMeetingDataRepository>()
            MeetingsMaintainer(meetingDataRepository, listOf(GoogleCalendarSync(), LocalCalendarSync(context)))
        }

        viewModel {
            val meetingsMaintainer = get<MeetingsMaintainer>()
            MeetingsViewModel(meetingsMaintainer, get())
        }

        factory {
            val meetingsViewModel = get<MeetingsViewModel>()
            MeetingWidgetRemoteViewFactory(context, meetingsViewModel)
        }
    }
}