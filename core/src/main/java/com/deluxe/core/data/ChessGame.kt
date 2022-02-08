package com.deluxe.core.data

import com.deluxe.core.formatTime
import java.io.Serializable
import java.lang.Exception

data class ChessGame(val name: String, val time : Long, val increment : Int, val id : Long = 0L) : Serializable {
    val player1 = Player(Players.PLAYER_ONE.playerNumber, time, 0)
    val player2 = Player(Players.PLAYER_TWO.playerNumber, time, 0)

    private var activePlayer : Player? = null

    fun switchPlayer(timeRemaining : Long) : Player {
        if (activePlayer == null) throw Exception("You need to call start() method before switching players")
        activePlayer!!.time = timeRemaining
        activePlayer!!.movesMade++
        if (activePlayer!!.movesMade >= 1) activePlayer!!.time += increment
        activePlayer = if (activePlayer == player1) player2 else player1
        return activePlayer!!
    }

    fun getActivePlayerRemainingTime() = activePlayer?.getTimeInMillis()

    fun getActivePlayerNumber() = activePlayer?.playerNumber

    fun getDuration() : String {
       return (time*1000).formatTime()
    }

    override fun toString(): String {
        return name
    }

    fun start() {
        activePlayer = player1
    }

    fun isGameStarted(): Boolean = activePlayer != null

}