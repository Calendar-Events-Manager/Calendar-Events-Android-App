package com.mymeetings.android.view.widgets

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.mymeetings.android.R
import com.mymeetings.android.model.Meeting
import com.mymeetings.android.view.activities.ui.home.MeetingsViewModel
import kotlinx.android.synthetic.main.widget_meetings.view.*
import org.koin.android.ext.android.get


class MeetingAppWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return get<MeetingWidgetRemoteViewFactory>()
    }
}

class MeetingWidgetRemoteViewFactory(
    private val context: Context,
    private val meetingsViewModel: MeetingsViewModel
) : RemoteViewsService.RemoteViewsFactory, LifecycleOwner {

    private var meetings : List<Meeting>? = null

    private val lifecycleDispatcher: WidgetServiceLifecycleDispatcher =
        WidgetServiceLifecycleDispatcher(this)


    override fun onCreate() {
        lifecycleDispatcher.onConstructor()
        meetingsViewModel.meetingsLiveData.observe(this, Observer {
            meetings = it
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, MeetingAppWidgetProvider::class.java)
            )
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView)
            //TODO refresh this adapter.
        })
        meetingsViewModel.syncEvents()
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.item_meeting)
    }

    override fun getItemId(position: Int): Long {
        return meetings?.get(position)?.meetingId?:position.toLong()
    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity();
        Binder.restoreCallingIdentity(identityToken);
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewAt(position: Int): RemoteViews {
        if(position != AdapterView.INVALID_POSITION) {
            val meeting = meetings?.get(position)
            return RemoteViews(context.packageName, R.layout.item_meeting).apply {
                setTextViewText(R.id.titleText, meeting?.meetingTitle?:"")
            }
        }

        return loadingView
    }

    override fun getCount(): Int {
        return meetings?.size?:0
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