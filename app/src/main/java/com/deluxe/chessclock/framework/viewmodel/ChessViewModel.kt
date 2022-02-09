package com.deluxe.chessclock.framework.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.deluxe.chessclock.framework.UseCases
import com.deluxe.chessclock.framework.di.ApplicationModule
import com.deluxe.chessclock.framework.di.DaggerViewModelComponent
import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Players
import com.deluxe.core.data.Resource
import javax.inject.Inject

class ChessViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases: UseCases

    var activeGame: ChessGame? = null
        private set

    val gameStarted: ObservableField<Boolean> = ObservableField(false)

    val player1Moves: ObservableField<Int> = ObservableField()
    val player2Moves: ObservableField<Int> = ObservableField()

    fun getChessGames() = liveData {
        emit(Resource.loading(null))
        emit(Resource.success(useCases.getAllChessGames()))
    }

    fun getActivePlayerNumber(): Int =
        activeGame?.getActivePlayerNumber()?: 0

    fun setActiveGame(chessGame: ChessGame) {
        this.activeGame = chessGame
        player1Moves.set(0)
        player2Moves.set(0)
        updateGameStartedObserver(false)
    }

    fun switchPlayer(remainingTime: Long) {
        val activePlayer = activeGame?.switchPlayer(if (remainingTime == 0L) activeGame!!.time else remainingTime)
        notifyObservers(activePlayer?.playerNumber?:-1)
    }

    private fun notifyObservers(playerNumber: Int) {
        when (playerNumber) {
            Players.PLAYER_ONE.playerNumber -> {
                player1Moves.set(player1Moves.get()?.plus(1))
                player1Moves.notifyChange()
            }
            Players.PLAYER_TWO.playerNumber -> {
                player2Moves.set(player2Moves.get()?.plus(1))
                player2Moves.notifyChange()
            }
        }
    }

    private fun startGame() {
        activeGame?.start()
        updateGameStartedObserver(true)
    }

    fun isGameResumed(): Boolean = activeGame?.isGameResumed() == true

    fun pauseGame(playerTimeRemaining : Long?) {
        activeGame?.pause(playerTimeRemaining?:0L)
        updateGameStartedObserver(false)
    }

    fun resumeGame() {
        if (activeGame?.isGameStarted() == false) startGame()
        else activeGame?.resume()
        updateGameStartedObserver(true)
    }

    fun resetGame() {
        activeGame?.reset()
        updateGameStartedObserver(false)
    }

    private fun updateGameStartedObserver(isStarted : Boolean) = gameStarted.set(isStarted)

    init {
        DaggerViewModelComponent
            .builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }
}