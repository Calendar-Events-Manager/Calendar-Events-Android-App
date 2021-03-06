package com.mymeetings.android.view.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.mymeetings.android.R

class CalendarEventsWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        context?.let { ctx ->
            appWidgetIds?.forEach { id ->
                val intent: Intent = Intent(context, CalendarEventWidgetService::class.java).apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
                    data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
                }

                // Get the layout for the App Widget and attach an on-click listener
                // to the button
                val views: RemoteViews = RemoteViews(
                    ctx.packageName,
                    R.layout.widget_meetings
                ).apply {
                    setRemoteAdapter(R.id.listView, intent)
                    setEmptyView(R.id.listView, R.id.empty_widget_layout)
                }

                // Tell the AppWidgetManager to perform an update on the current app widget
                appWidgetManager?.updateAppWidget(id, views)
            }
        }

    }
}