package com.deluxe.core

import java.util.concurrent.TimeUnit

fun Long.formatTime(showMillis: Boolean = false): String {
    var formatted = String.format(
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(this),
        TimeUnit.MILLISECONDS.toMinutes(this) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this)),
        TimeUnit.MILLISECONDS.toSeconds(this) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
    )
    if (showMillis) formatted += String.format(
        ":%d",
        (this - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(this))) / 100
    )
    return if (formatted.startsWith("00:")) formatted.substring(3)
    else formatted
}
