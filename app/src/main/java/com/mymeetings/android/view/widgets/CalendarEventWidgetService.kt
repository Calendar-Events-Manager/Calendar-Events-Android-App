package com.mymeetings.android.view.widgets

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.Observer
import com.mymeetings.android.R
import com.mymeetings.android.model.CalendarEventAlertUIModel
import com.mymeetings.android.view.viewModels.CalendarEventsViewModel
import org.koin.android.ext.android.get


class CalendarEventWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return get<CalendarEventWidgetRemoteViewFactory>()
    }
}

class CalendarEventWidgetRemoteViewFactory(
    private val context: Context,
    private val calendarEventsViewModel: CalendarEventsViewModel
) : RemoteViewsService.RemoteViewsFactory {

    private val calendarEventWidgetViewProvider = CalendarEventWidgetViewProvider(context)

    private val observer = Observer<List<CalendarEventAlertUIModel>> {
        calendarEventWidgetViewProvider.updateCalendarEventsAlertUIData(it)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(context, CalendarEventsWidgetProvider::class.java)
        )
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView)
    }

    override fun onCreate() {
        calendarEventsViewModel.calendarEventAlertsLiveData.observeForever(observer)
        calendarEventsViewModel.getEvents()
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.item_meeting)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewAt(position: Int): RemoteViews {
        if (position != AdapterView.INVALID_POSITION) {
            return calendarEventWidgetViewProvider.getView(position)
        }

        return loadingView
    }

    override fun getCount(): Int {
        return calendarEventWidgetViewProvider.getTotalViewItemCount()
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
        calendarEventsViewModel.calendarEventAlertsLiveData.removeObserver(observer)
    }
}