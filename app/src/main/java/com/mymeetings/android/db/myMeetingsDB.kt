package com.mymeetings.android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MeetingsDBModel::class], version = 1)
abstract class MyMeetingsDatabase : RoomDatabase() {

    abstract fun getMeetingsDao(): MeetingsDao

    companion object {
        @Volatile
        private var INSTANCE: MyMeetingsDatabase? = null

        fun getDb(context: Context): MyMeetingsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyMeetingsDatabase::class.java,
                    "my_meetings_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}