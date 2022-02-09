package com.deluxe.chessclock.framework.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.deluxe.chessclock.framework.UseCases
import com.deluxe.chessclock.framework.data.model.AddChessGame
import com.deluxe.chessclock.framework.data.model.ChessGamePlaceholder
import com.deluxe.chessclock.framework.di.ApplicationModule
import com.deluxe.chessclock.framework.di.DaggerViewModelComponent
import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.GameState
import com.deluxe.core.data.Players
import com.deluxe.core.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChessViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases: UseCases

    var activeGame: ChessGame? = null
        private set

    val gameState: ObservableField<GameState> = ObservableField(GameState.NOT_STARTED)

    val player1Moves: ObservableField<Int> = ObservableField()
    val player2Moves: ObservableField<Int> = ObservableField()

    fun getChessGames() = liveData {
        emit(Resource.loading(null))
        val listOfGames = useCases.getAllChessGames()
        val gamesWithOptionToAdd : ArrayList<ChessGame> = arrayListOf(AddChessGame())
        gamesWithOptionToAdd.addAll(listOfGames)
        if (gamesWithOptionToAdd.size % 2 == 1) gamesWithOptionToAdd.add(ChessGamePlaceholder())
        emit(Resource.success(gamesWithOptionToAdd))
    }

    fun saveChessGame(chessGame: ChessGame) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            useCases.insertChessGame.invoke(chessGame)
        }
    }

    fun getActivePlayerNumber(): Int =
        activeGame?.getActivePlayerNumber() ?: 0

    fun setActiveGame(chessGame: ChessGame) {
        this.activeGame = chessGame
        player1Moves.set(0)
        player2Moves.set(0)
        updateGameStartedObserver { GameState.NOT_STARTED }
    }

    fun switchPlayer(remainingTime: Long) {
        val previouslyActivePlayer = activeGame?.getActivePlayerNumber()
        activeGame?.switchPlayer(if (remainingTime == 0L) activeGame!!.time else remainingTime)
        notifyObservers(previouslyActivePlayer ?: -1)
    }

    fun stopGame() = updateGameStartedObserver { activeGame?.stop() }

    fun isGameResumed(): Boolean = activeGame?.isGameResumed() == true

    fun pauseGame(playerTimeRemaining: Long?) =
        updateGameStartedObserver { activeGame?.pause(playerTimeRemaining ?: 0L) }

    fun resumeGame() = updateGameStartedObserver {
        if (activeGame?.isGameStarted() == false) activeGame?.start()
        else activeGame?.resume()
    }

    fun resetGame() = updateGameStartedObserver { activeGame?.reset() }

    private fun updateGameStartedObserver(getGameState: () -> GameState?) =
        this.gameState.set(getGameState.invoke())

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

    init {
        DaggerViewModelComponent
            .builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }
}
