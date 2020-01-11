package com.mymeetings.android.debug

import android.util.Log
import com.mymeetings.android.BuildConfig

object ConsoleLog {

    val isDebug = BuildConfig.DEBUG

    fun i(tag : String? = "APP", message : String) {
        if(isDebug) {
            Log.i(tag, message)
        }
    }

    fun d(tag : String? = "APP", message : String) {
        if(isDebug) {
            Log.d(tag, message)
        }
    }

    fun w(tag : String? = "APP", message : String) {
        if(isDebug) {
            Log.w(tag, message)
        }
    }
}