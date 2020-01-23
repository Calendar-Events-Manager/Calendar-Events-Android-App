package com.mymeetings.android.debug

import android.util.Log
import com.mymeetings.android.BuildConfig

class ConsoleLog(private val enableLog : Boolean) {

    fun i(tag : String? = "APP", message : String) {
        if(enableLog) {
            Log.i(tag, message)
        }
    }

    fun d(tag : String? = "APP", message : String) {
        if(enableLog) {
            Log.d(tag, message)
        }
    }

    fun w(tag : String? = "APP", message : String) {
        if(enableLog) {
            Log.w(tag, message)
        }
    }
}