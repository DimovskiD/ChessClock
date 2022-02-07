package com.deluxe.core.data

import java.text.SimpleDateFormat

open class ChessGame(private val name: String, clock: Clock) {
    private val player1 = Player(clock, 0)
    private val player2 = Player(clock, 0)

    private var activePlayer = player1

    fun switchPlayer(timeElapsed : Long) {
        activePlayer.clock.time = activePlayer.clock.time - timeElapsed + activePlayer.clock.increment
        activePlayer.movesMade++
        activePlayer = if (activePlayer == player1) player2 else player1
    }

    @Suppress("SimpleDateFormat")
    fun getDuration() : String {
        val df = SimpleDateFormat("mm:ss")
        return df.format(player1.clock.getTimeInMillis())
    }

    fun getIncrement() = player1.clock.increment

    override fun toString(): String {
        return name
    }
}