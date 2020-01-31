package com.mymeetings.android.view.widgets

import android.content.Context
import android.view.View
import android.widget.RemoteViews
import com.mymeetings.android.R
import com.mymeetings.android.model.strategies.ViewAlertType
import com.mymeetings.android.view.uiModels.CalendarEventAlertUIModel

class CalendarEventWidgetViewProvider(
    private val context: Context
) {

    private val calendarEventViewAlerts = mutableListOf<CalendarEventAlertUIModel>()

    fun updateCalendarEventsAlertUIData(calendarEventAlertUIModels: List<CalendarEventAlertUIModel>) {
        calendarEventViewAlerts.clear()
        calendarEventViewAlerts.addAll(calendarEventAlertUIModels)
    }

    fun getTotalViewItemCount() = calendarEventViewAlerts.size

    fun getView(position: Int) = RemoteViews(context.packageName, R.layout.item_meeting).apply {
        val calendarEventViewAlert = calendarEventViewAlerts[position]
        if (calendarEventViewAlert.viewAlertType == ViewAlertType.RUNNING) {
            setTextViewText(R.id.runningTitleText, calendarEventViewAlert.title)
            setTextViewText(R.id.runningTimeText, calendarEventViewAlert.eventTimeLine)
            setViewVisibility(R.id.upcomingLayout, View.GONE)
            setViewVisibility(R.id.runningLayout, View.VISIBLE)
        } else {
            setTextViewText(R.id.titleText, calendarEventViewAlert.title)
            setTextViewText(R.id.timeText, calendarEventViewAlert.eventTimeLine)
            setTextViewText(R.id.timeLeftToStartText, calendarEventViewAlert.timeLeft)
            setViewVisibility(R.id.upcomingLayout, View.VISIBLE)
            setViewVisibility(R.id.runningLayout, View.GONE)

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

            setInt(
                R.id.upcomingLayout,
                "setBackgroundColor",
                context.getColor(colorResId)
            )
        }
    }
}