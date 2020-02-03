package com.mymeetings.android.view.widgets

import android.content.Context
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.mymeetings.android.R
import com.mymeetings.android.model.extensions.toUiModelCollection
import com.mymeetings.android.utils.ClockUtils
import com.mymeetings.android.view.viewModels.CalendarEventsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarEventWidgetRemoteViewFactory(
    private val context: Context,
    private val calendarEventsViewModel: CalendarEventsViewModel,
    private val clockUtils: ClockUtils
) : RemoteViewsService.RemoteViewsFactory {

    private val calendarEventWidgetViewProvider =
        CalendarEventWidgetViewProvider(context, clockUtils)

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(
            context.packageName,
            R.layout.item_meeting
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onDataSetChanged() {
        CoroutineScope(Dispatchers.Default).launch {
            calendarEventWidgetViewProvider.updateCalendarEventsAlertUIData(
                calendarEventsViewModel.getCalendarEvents().toUiModelCollection(clockUtils)
            )
        }
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

    override fun onDestroy() {}
}