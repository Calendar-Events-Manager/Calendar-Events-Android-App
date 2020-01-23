package com.mymeetings.android.view.widgets

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.view.View
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.Observer
import com.mymeetings.android.R
import com.mymeetings.android.model.CalendarEvent
import com.mymeetings.android.model.CalendarEventWithAlert
import com.mymeetings.android.model.managers.CalendarEventAlertManager
import com.mymeetings.android.model.strategies.ViewAlertType
import com.mymeetings.android.utils.ClockUtils
import com.mymeetings.android.view.activities.ui.home.CalendarEventsViewModel
import org.koin.android.ext.android.get


class CalendarEventWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return get<CalendarEventWidgetRemoteViewFactory>()
    }
}

class CalendarEventWidgetRemoteViewFactory(
    private val context: Context,
    private val calendarEventsViewModel: CalendarEventsViewModel,
    private val calendarEventAlertManager: CalendarEventAlertManager,
    private val clockUtils: ClockUtils
) : RemoteViewsService.RemoteViewsFactory {

    private var calendarEventsWithViewAlertType: List<CalendarEventWithAlert>? = null

    private val observer = Observer<List<CalendarEvent>> {
        calendarEventsWithViewAlertType = calendarEventAlertManager.getCalendarEventAlerts(it)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(context, CalendarEventsWidgetProvider::class.java)
        )
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView)
    }

    override fun onCreate() {
        calendarEventsViewModel.getCalendarEventLiveData().observeForever(observer)
        calendarEventsViewModel.getEvents()
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.item_meeting)
    }

    override fun getItemId(position: Int): Long {
        return calendarEventsWithViewAlertType?.get(position)?.calendarEvent?.id
            ?: position.toLong()
    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewAt(position: Int): RemoteViews {
        if (position != AdapterView.INVALID_POSITION && calendarEventsWithViewAlertType?.size ?: 0 > position) {
            return RemoteViews(context.packageName, R.layout.item_meeting).apply {
                calendarEventsWithViewAlertType?.get(position)?.let {
                    setDataToRemoteViews(this, it)
                }
            }
        }

        return loadingView
    }

    private fun setDataToRemoteViews(
        remoteViews: RemoteViews,
        calendarEventWithAlert: CalendarEventWithAlert
    ) {
        val calendarEvent = calendarEventWithAlert.calendarEvent
        val calendarEventViewAlert = calendarEventWithAlert.eventViewAlert

        val startTimeToEndTimeString =
            "${clockUtils.getFormattedTime(calendarEvent.startTime)} - ${clockUtils.getFormattedTime(
                calendarEvent.endTime
            )}"

        if (calendarEventViewAlert.viewAlertType == ViewAlertType.RUNNING) {
            remoteViews.setTextViewText(R.id.runningTitleText, calendarEvent.title)
            remoteViews.setTextViewText(R.id.runningTimeText, startTimeToEndTimeString)
            remoteViews.setViewVisibility(R.id.upcomingLayout, View.GONE)
            remoteViews.setViewVisibility(R.id.runningLayout, View.VISIBLE)
        } else {
            remoteViews.setTextViewText(R.id.titleText, calendarEvent.title)
            remoteViews.setTextViewText(R.id.timeText, startTimeToEndTimeString)
            remoteViews.setTextViewText(
                R.id.timeLeftToStartText,
                clockUtils.getTimeLeft(calendarEvent.startTime)
            )
            remoteViews.setViewVisibility(R.id.upcomingLayout, View.VISIBLE)
            remoteViews.setViewVisibility(R.id.runningLayout, View.GONE)

            val colorResId = when (calendarEventViewAlert.viewAlertType) {
                ViewAlertType.PRIORITY -> {
                    android.R.color.holo_red_light
                }
                ViewAlertType.NORMAL -> {
                    android.R.color.holo_orange_light
                }
                else -> {
                    android.R.color.holo_blue_light
                }
            }

            remoteViews.setInt(
                R.id.upcomingLayout,
                "setBackgroundColor",
                context.getColor(colorResId)
            )
        }
    }

    override fun getCount(): Int {
        return calendarEventsWithViewAlertType?.size ?: 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
        calendarEventsViewModel.getCalendarEventLiveData().removeObserver(observer)
    }
}