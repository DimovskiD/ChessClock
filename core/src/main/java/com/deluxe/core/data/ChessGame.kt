package com.deluxe.core.data

import com.deluxe.core.formatTime
import java.lang.Exception

open class ChessGame(val name: String, val time: Long, val increment: Int, val id: Long = -1L)  {

    private val timeInMilliSeconds = time * 1000
    private val incrementInMilliSeconds = increment * 1000

    private val players = listOf(
        Player(Players.PLAYER_ONE.playerNumber, timeInMilliSeconds, 0),
        Player(Players.PLAYER_TWO.playerNumber, timeInMilliSeconds, 0)
    )

    private var gameState: GameState = GameState.NOT_STARTED

    fun switchPlayer(timeRemaining: Long): Player {
        if (getActivePlayer() == null) throw Exception("You need to call start() method before switching players")
        getActivePlayer()?.let { player ->
            player.movesMade++
            player.timeInMillis = timeRemaining +
                    if (shouldIncrement(player.movesMade)) incrementInMilliSeconds else 0
            players.forEach {
                it.isActive = !it.isActive
            }
        }
        return getActivePlayer()!!
    }

    fun getActivePlayerNumber() = getActivePlayer()?.playerNumber

    fun getDuration(inGame : Boolean = false): String = timeInMilliSeconds.formatTime(inGame)

    fun start() : GameState {
        players.first().isActive = true
        resume()
        return gameState
    }


    fun pause(timeRemaining: Long) : GameState {
        if (gameState == GameState.RESUMED) {
            getActivePlayer()?.timeInMillis = timeRemaining
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
        players.forEach { it.restart(timeInMilliSeconds) }
        gameState = GameState.NOT_STARTED
        return gameState
    }

    fun isGameStarted(): Boolean = gameState != GameState.NOT_STARTED && gameState != GameState.FINISHED
    fun isGameResumed(): Boolean = gameState == GameState.RESUMED

    fun getPlayer(isActive : Boolean) : Player? = players.firstOrNull() { it.isActive == isActive}
    fun getTimeInMillis(): Long = timeInMilliSeconds

    private fun getActivePlayer() = getPlayer(true)
    private fun getInactivePlayer() = getPlayer(false)

    private fun shouldIncrement(movesMade: Int): Boolean = movesMade >= 1

    override fun toString(): String = name
}