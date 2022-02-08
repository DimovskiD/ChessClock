package com.deluxe.core.data

import com.deluxe.core.formatTime

class Player(val playerNumber : Int, var time: Long, var movesMade : Int) {
    fun getTimeInMillis(): Long  = time * 1000

    fun getFormattedTime(): String = getTimeInMillis().formatTime()
    fun restart(time : Long) {
        this.time = time
        this.movesMade = 0
    }
}