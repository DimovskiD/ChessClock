package com.deluxe.core.data

class Player(var time: Long, val increment : Int,  var movesMade : Int) {
    fun getTimeInMillis(): Long  = time * 1000
}