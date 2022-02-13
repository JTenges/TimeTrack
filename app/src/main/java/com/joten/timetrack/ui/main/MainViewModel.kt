package com.joten.timetrack.ui.main

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.time.Duration
import java.time.ZonedDateTime

class MainViewModel: ViewModel() {
    var startDate: ZonedDateTime? = null

    val duration: MutableLiveData<Duration?> by lazy {
        MutableLiveData<Duration?>()
    }

    private var refreshTimer: CountDownTimer = object : CountDownTimer(1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            updateDuration()
            this.start()
        }
    }

    private fun updateDuration() {
        duration.value = Duration.between(startDate, ZonedDateTime.now())
    }

    fun startTiming() {
        startDate = ZonedDateTime.now()
        updateDuration()
        refreshTimer.cancel()
        refreshTimer.start()
    }
}