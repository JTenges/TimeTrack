package com.example.timetrack

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.timetrack.ui.theme.TimeTrackTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackTimeScreen()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

fun Duration.minutePart(): Long {
    return (this.seconds % 3600) / 60;
}

fun Duration.hourPart(): Long {
    return this.seconds / 3600;
}

fun Duration.formatted(): String {
    return "${this.hourPart()}:${this.minutePart()}:${this.seconds}"
}

class ScreenState {
    var startDate: ZonedDateTime? = null
        set(date) {
            field = date
            if (date != null) {
                updateDuration()
                refreshTimer.cancel()
                refreshTimer.start()
            }
        }

    var duration: MutableState<Duration?> = mutableStateOf(null)
    private var refreshTimer: CountDownTimer

    private fun updateDuration() {
        duration.value = Duration.between(startDate, ZonedDateTime.now())
    }

    init {
        refreshTimer =  object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                updateDuration()
                this.start()
            }
        }
    }
}

@Composable
fun TrackTimeScreen() {
    val screenState = ScreenState()
    TimeTrackTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Column {
                Greeting(
                    screenState.duration.value?.formatted() ?: "Start Timer"
                )
                Button(onClick = { screenState.startDate = ZonedDateTime.now() }) {
                    Text(text = "Start Activity")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TrackTimeScreen()
}