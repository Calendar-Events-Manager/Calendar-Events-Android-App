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

        single { get<CalendarEventsReminderDatabase>().getMeetingsDao() }

        single { RoomCalendarEventsDataRepository(get(), get()) }

        single { ClockUtils() }

        single { CalendarEventAlertManager(get()) }

        single { GoogleCalendarFetchStrategy() }

        single { LocalCalendarFetchStrategy(context) }

        single {
            CalendarEventsSyncManager(
                get<RoomCalendarEventsDataRepository>(),
                listOf(
                    get<LocalCalendarFetchStrategy>(),
                    get<GoogleCalendarFetchStrategy>()
                ),
                get()
            )
        }

        viewModel { CalendarEventsViewModel(get()) }

        factory { CalendarEventWidgetRemoteViewFactory(context, get(), get(), get()) }
    }
}