package com.mymeetings.android.utils

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import com.mymeetings.android.R
import com.mymeetings.android.view.widgets.CalendarEventsWidgetProvider

class WidgetUtils(private val context: Context) {

    fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(context, CalendarEventsWidgetProvider::class.java)
        )
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView)
    }
}