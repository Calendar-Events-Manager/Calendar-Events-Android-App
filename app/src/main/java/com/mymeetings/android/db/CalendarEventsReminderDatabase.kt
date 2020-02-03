package com.mymeetings.android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalendarEventDbModel::class], version = 1, exportSchema = false)
abstract class CalendarEventsReminderDatabase : RoomDatabase() {

    abstract fun getMeetingsDao(): CalendarEventsDao

    companion object {
        @Volatile
        private var INSTANCE: CalendarEventsReminderDatabase? = null

        fun getDb(context: Context): CalendarEventsReminderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalendarEventsReminderDatabase::class.java,
                    "calendar_events_reminder"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}