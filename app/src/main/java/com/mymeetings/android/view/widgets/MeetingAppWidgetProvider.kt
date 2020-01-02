package com.mymeetings.android.view.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.mymeetings.android.R
import com.mymeetings.android.view.activities.MeetingListActivity

class MeetingAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        context?.let { ctx ->
            appWidgetIds?.forEach { id ->
                val pendingIntent: PendingIntent = Intent(context, MeetingListActivity::class.java)
                    .let { intent ->
                        PendingIntent.getActivity(context, 0, intent, 0)
                    }

                // Get the layout for the App Widget and attach an on-click listener
                // to the button
                val views: RemoteViews = RemoteViews(
                    ctx.packageName,
                    R.layout.widget_meetings
                ).apply {
                    setOnClickPendingIntent(R.id.textView, pendingIntent)
                }

                // Tell the AppWidgetManager to perform an update on the current app widget
                appWidgetManager?.updateAppWidget(id, views)
            }
        }

    }
}