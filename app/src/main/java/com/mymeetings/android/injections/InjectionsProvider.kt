package com.mymeetings.android.injections

import android.content.Context
import com.mymeetings.android.BuildConfig
import com.mymeetings.android.db.CalendarEventsReminderDatabase
import com.mymeetings.android.db.repositories.RoomCalendarEventsDataRepository
import com.mymeetings.android.debug.ConsoleLog
import com.mymeetings.android.model.strategies.GoogleCalendarFetchStrategy
import com.mymeetings.android.model.strategies.LocalCalendarFetchStrategy
import com.mymeetings.android.model.managers.CalendarEventAlertManager
import com.mymeetings.android.model.managers.CalendarEventsSyncManager
import com.mymeetings.android.utils.ClockUtils
import com.mymeetings.android.view.viewModels.CalendarEventsViewModel
import com.mymeetings.android.view.widgets.CalendarEventWidgetRemoteViewFactory
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class InjectionsProvider(private val context: Context) {

    val module = module {

        single { CalendarEventsReminderDatabase.getDb(context) }

        single { get<CalendarEventsReminderDatabase>().getMeetingsDao() }

        single { RoomCalendarEventsDataRepository(get(), get()) }

        single { ClockUtils() }

        single {
            CalendarEventAlertManager()
        }

        single { GoogleCalendarFetchStrategy() }

        single { LocalCalendarFetchStrategy(context) }

        single {
            //Enabling log only for debug builds.
            ConsoleLog(BuildConfig.DEBUG)
        }

        single {
            CalendarEventsSyncManager(
                get<RoomCalendarEventsDataRepository>(),
                listOf(
                    get<LocalCalendarFetchStrategy>()
                ),
                get()
            )
        }

        viewModel { CalendarEventsViewModel(get(), get(), get()) }

        factory { CalendarEventWidgetRemoteViewFactory(context, get()) }
    }
}