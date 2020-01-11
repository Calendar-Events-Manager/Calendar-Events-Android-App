package com.mymeetings.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.concurrent.TimeUnit

@Entity(tableName = "calendar_events")
data class CalendarEventsDbModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "start_time")
    var startTime : Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time")
    var endTime : Long = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10),

    @ColumnInfo(name = "is_done")
    var isDone : Boolean = false
)