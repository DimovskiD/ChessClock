package com.deluxe.core.data

class Player(val playerNumber : Int, var timeInMillis: Long, var movesMade : Int, var isActive : Boolean = false) {

    fun restart(time : Long) {
        this.timeInMillis = time
        this.movesMade = 0
        this.isActive = false
    }
}