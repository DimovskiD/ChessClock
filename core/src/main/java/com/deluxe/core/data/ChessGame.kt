package com.deluxe.core.data

import com.deluxe.core.formatTime
import java.io.Serializable
import java.lang.Exception

data class ChessGame(val name: String, val time: Long, val increment: Int, val id: Long = 0L) :
    Serializable {

    private val players = listOf(
        Player(Players.PLAYER_ONE.playerNumber, time, 0),
        Player(Players.PLAYER_TWO.playerNumber, time, 0)
    )

    private var gameState: GameState = GameState.NOT_STARTED

    fun switchPlayer(timeRemaining: Long): Player {
        if (getActivePlayer() == null) throw Exception("You need to call start() method before switching players")
        getActivePlayer()?.let { player ->
            player.time = timeRemaining
            player.movesMade++
            if (shouldIncrement(player.movesMade)) player.time += increment
            players.forEach {
                it.isActive = !it.isActive
            }
        }
        return getActivePlayer()!!
    }

    fun getActivePlayerNumber() = getActivePlayer()?.playerNumber

    fun getDuration(): String = (time * 1000).formatTime()

    fun start() : GameState {
        players.first().isActive = true
        resume()
        return gameState
    }


    fun pause(timeRemaining: Long) : GameState {
        if (gameState == GameState.RESUMED) {
            getActivePlayer()?.time = timeRemaining
            gameState = GameState.PAUSED
        }
        return gameState
    }

    fun resume() : GameState {
        if (gameState == GameState.PAUSED || gameState == GameState.NOT_STARTED) gameState = GameState.RESUMED
        return gameState
    }

    fun stop() : GameState {
        gameState = GameState.FINISHED
        return gameState
    }

    fun reset() : GameState {
        players.forEach { it.restart(time) }
        gameState = GameState.NOT_STARTED
        return gameState
    }

    fun isGameStarted(): Boolean = gameState != GameState.NOT_STARTED && gameState != GameState.FINISHED
    fun isGameResumed(): Boolean = gameState == GameState.RESUMED

    fun getPlayer(isActive : Boolean) : Player? = players.firstOrNull() { it.isActive == isActive}

    private fun getActivePlayer() = getPlayer(true)
    private fun getInactivePlayer() = getPlayer(false)

    private fun shouldIncrement(movesMade: Int): Boolean = movesMade >= 1

    override fun toString(): String = name
}