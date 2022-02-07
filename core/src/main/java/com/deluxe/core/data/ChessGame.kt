package com.deluxe.core.data

import java.text.SimpleDateFormat

open class ChessGame(val name: String, val time : Long, val increment : Int, val id : Long = 0L) {
    private val player1 = Player(time, increment, 0)
    private val player2 = Player(time, increment, 0)

    private var activePlayer = player1

    fun switchPlayer(timeElapsed : Long) {
        activePlayer.time = activePlayer.time - timeElapsed + activePlayer.increment
        activePlayer.movesMade++
        activePlayer = if (activePlayer == player1) player2 else player1
    }

    @Suppress("SimpleDateFormat")
    fun getDuration() : String {
        val df = SimpleDateFormat("mm:ss")
        return df.format(player1.getTimeInMillis())
    }

    override fun toString(): String {
        return name
    }
}