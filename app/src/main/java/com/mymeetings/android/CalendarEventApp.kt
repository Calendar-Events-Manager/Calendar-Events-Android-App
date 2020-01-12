package com.mymeetings.android

import android.app.Application
import com.mymeetings.android.injections.InjectionsProvider
import org.koin.android.ext.android.startKoin

class CalendarEventApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(InjectionsProvider(this).module))
    }
}