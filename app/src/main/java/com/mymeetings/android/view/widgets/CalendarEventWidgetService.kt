package com.mymeetings.android.view.widgets

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.mymeetings.android.R
import com.mymeetings.android.model.ViewAlertType
import com.mymeetings.android.model.managers.CalendarEventAlertManager
import com.mymeetings.android.model.CalendarEventWithViewAlert
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
    private val calendarEventAlertManager: CalendarEventAlertManager
) : RemoteViewsService.RemoteViewsFactory, LifecycleOwner {

    private var calendarEventsWithViewAlertType: List<CalendarEventWithViewAlert>? = null

    private val lifecycleDispatcher: WidgetServiceLifecycleDispatcher =
        WidgetServiceLifecycleDispatcher(this)

    override fun onCreate() {
        lifecycleDispatcher.onConstructor()
        calendarEventsViewModel.getCalendarEventLiveData().observe(this, Observer {
            calendarEventsWithViewAlertType = calendarEventAlertManager.getCalendarEventAlerts(it)
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, CalendarEventsWidgetProvider::class.java)
            )
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView)
        })
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
        val identityToken = Binder.clearCallingIdentity();
        Binder.restoreCallingIdentity(identityToken);
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewAt(position: Int): RemoteViews {
        if (position != AdapterView.INVALID_POSITION && calendarEventsWithViewAlertType?.size ?: 0 > position) {
            val calendarEventWithAlertPair = calendarEventsWithViewAlertType?.get(position)
            return RemoteViews(context.packageName, R.layout.item_meeting).apply {
                calendarEventWithAlertPair?.let {
                    val startTimeToEndTimeString = "${it.startTime} - ${it.endTime}"
                    if (it.viewAlertType == ViewAlertType.RUNNING) {
                        setTextViewText(R.id.runningTitleText, it.calendarEvent.title)
                        setTextViewText(R.id.runninTimeText, startTimeToEndTimeString)
                        setViewVisibility(R.id.upcomingLayout, View.GONE)
                        setViewVisibility(R.id.runningLayout, View.VISIBLE)
                    } else {
                        setTextViewText(R.id.titleText, it.calendarEvent.title)
                        setTextViewText(R.id.timeText, startTimeToEndTimeString)
                        setTextViewText(R.id.timeLeftToStartText, it.timeLeftToStart)
                        setViewVisibility(R.id.upcomingLayout, View.VISIBLE)
                        setViewVisibility(R.id.runningLayout, View.GONE)

                        when (it.viewAlertType) {
                            ViewAlertType.PRIORITY -> {
                                setInt(
                                    R.id.upcomingLayout,
                                    "setBackgroundColor",
                                    context.getColor(android.R.color.holo_red_light)
                                )
                            }
                            ViewAlertType.NORMAL -> {
                                setInt(
                                    R.id.upcomingLayout,
                                    "setBackgroundColor",
                                    context.getColor(android.R.color.holo_orange_light)
                                )
                            }
                            else -> {
                                setInt(
                                    R.id.upcomingLayout,
                                    "setBackgroundColor",
                                    context.getColor(android.R.color.holo_blue_light)
                                )
                            }
                        }
                    }
                }

            }
        }

        return loadingView
    }

    override fun getCount(): Int {
        return calendarEventsWithViewAlertType?.size ?: 0
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
        lifecycleDispatcher.onDestroyed()
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleDispatcher.lifecycle
    }
}

class WidgetServiceLifecycleDispatcher(provider: LifecycleOwner) {
    private val mRegistry = LifecycleRegistry(provider)
    private val mHandler: Handler = Handler()
    private var mLastDispatchRunnable: DispatchRunnable? = null
    private fun postDispatchRunnable(event: Lifecycle.Event) {
        mLastDispatchRunnable?.run()
        mLastDispatchRunnable = DispatchRunnable(mRegistry, event)
        mLastDispatchRunnable?.let {
            mHandler.postAtFrontOfQueue(it)
        }
    }

    /**
     * Must be a first call in [android.appwidget.AppWidgetProvider.AppWidgetProvider] constructor, even before super call.
     */
    fun onConstructor() {
        postDispatchRunnable(Lifecycle.Event.ON_CREATE)
        postDispatchRunnable(Lifecycle.Event.ON_START)
    }

    /**
     * Must be a first call in [android.appwidget.AppWidgetProvider.onDeleted] method, even before super.onDeleted call.
     */
    fun onDestroyed() {
        postDispatchRunnable(Lifecycle.Event.ON_PAUSE)
        postDispatchRunnable(Lifecycle.Event.ON_STOP)
        postDispatchRunnable(Lifecycle.Event.ON_DESTROY)
    }

    /**
     * @return [Lifecycle] for the given [LifecycleOwner]
     */
    val lifecycle: Lifecycle
        get() = mRegistry


    internal class DispatchRunnable(
        private val mRegistry: LifecycleRegistry,
        val mEvent: Lifecycle.Event
    ) :
        Runnable {
        private var mWasExecuted = false
        override fun run() {
            if (!mWasExecuted) {
                mRegistry.handleLifecycleEvent(mEvent)
                mWasExecuted = true
            }
        }

    }

}