package com.mymeetings.android.view.activities.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mymeetings.android.model.MeetingsMaintainer
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(private val meetingsMaintainer: MeetingsMaintainer) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun syncEvents() {
        viewModelScope.launch {
            meetingsMaintainer.syncCloudCalendar()
        }
    }
}