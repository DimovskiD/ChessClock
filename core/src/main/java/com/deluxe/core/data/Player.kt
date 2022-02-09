package com.deluxe.core.data

class Player(val playerNumber : Int, var time: Long, var movesMade : Int, var isActive : Boolean = false) {

    fun getTimeInMillis(): Long  = time * 1000

    fun restart(time : Long) {
        this.time = time
        this.movesMade = 0
        this.isActive = false
    }
}