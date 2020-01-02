package com.mymeetings.android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings")
data class MeetingsDBModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "time")
    var time : Long = System.currentTimeMillis(),

    @ColumnInfo(name = "is_done")
    var isDone : Boolean = false
)