package com.joten.timetrack.util

import java.time.Duration

fun Duration.minutePart(): Long {
    return (this.seconds % 3600) / 60
}

fun Duration.hourPart(): Long {
    return this.seconds / 3600
}

fun Duration.formatted(): String {
    return "${this.hourPart()}:${this.minutePart()}:${this.seconds}"
}