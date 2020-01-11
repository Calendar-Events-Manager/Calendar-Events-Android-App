package com.mymeetings.android

import android.app.Application
import com.mymeetings.android.di.DIProvider
import org.koin.android.ext.android.startKoin

class CalendarEventApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(DIProvider(this).module))
    }
}