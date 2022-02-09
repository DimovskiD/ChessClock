package com.deluxe.core

import java.util.concurrent.TimeUnit

fun Long.formatTime() : String {
    val formatted = String.format(
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(this),
        TimeUnit.MILLISECONDS.toMinutes(this) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this)),
        TimeUnit.MILLISECONDS.toSeconds(this) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
    )
    if (formatted.startsWith("00:")) return formatted.substring(3)
    else return formatted
}
