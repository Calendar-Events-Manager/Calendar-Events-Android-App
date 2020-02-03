package com.mymeetings.android.view.widgets

import android.content.Intent
import android.widget.RemoteViewsService
import org.koin.android.ext.android.get

class CalendarEventWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return get<CalendarEventWidgetRemoteViewFactory>()
    }
}
