package com.deluxe.core.data

class Clock(var time : Long, val increment: Int) {
    fun getTimeInMillis(): Long  = time * 1000
}