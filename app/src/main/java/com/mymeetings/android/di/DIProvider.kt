package com.mymeetings.android.di

import android.content.Context
import com.mymeetings.android.db.CalendarEventsDao
import com.mymeetings.android.db.CalendarEventsReminderDatabase
import com.mymeetings.android.db.repositories.RoomCalendarEventsDataRepository
import com.mymeetings.android.model.*
import com.mymeetings.android.model.calendarFetchStrategies.GoogleCalendarFetchStrategy
import com.mymeetings.android.model.calendarFetchStrategies.LocalCalendarFetchStrategy
import com.mymeetings.android.utils.ClockUtils
import com.mymeetings.android.view.activities.ui.home.CalendarEventsViewModel
import com.mymeetings.android.view.widgets.CalendarEventWidgetRemoteViewFactory
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class DIProvider(private val context: Context) {

    val module = module {

        single { CalendarEventsReminderDatabase.getDb(context) }

        single {
            val myMeetingsDatabase = get<CalendarEventsReminderDatabase>()
            myMeetingsDatabase.getMeetingsDao()
        }

        single {
            val meetingsDao = get<CalendarEventsDao>()
            RoomCalendarEventsDataRepository(
                meetingsDao,
                get()
            )
        }

        single { ClockUtils() }

        single {
            val meetingDataRepository = get<RoomCalendarEventsDataRepository>()
            CalendarEventsSyncManager(meetingDataRepository, listOf(GoogleCalendarFetchStrategy(),
                LocalCalendarFetchStrategy(
                    context
                )
            ))
        }

        viewModel {
            val meetingsMaintainer = get<CalendarEventsSyncManager>()
            CalendarEventsViewModel(meetingsMaintainer)
        }

        factory {
            val meetingsViewModel = get<CalendarEventsViewModel>()
            CalendarEventWidgetRemoteViewFactory(context, meetingsViewModel)
        }
    }
}