package com.deluxe.core

import java.text.SimpleDateFormat

@Suppress("SimpleDateFormat")
fun Long.formatTime() : String {
    val df = SimpleDateFormat("mm:ss")
    return df.format(this)
}