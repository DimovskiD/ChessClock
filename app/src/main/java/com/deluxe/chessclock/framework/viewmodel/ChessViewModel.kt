package com.deluxe.chessclock.framework.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
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
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChessViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases: UseCases

    var selectedGame: ChessGame? = null
        private set

    private val _mutableShouldUpdateList : MutableLiveData<ChessGame?> = MutableLiveData(null)
    val shouldUpdateList : LiveData<ChessGame?> = _mutableShouldUpdateList

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
            selectedGame = chessGame
            val result = useCases.insertChessGame.invoke(chessGame)
            if (result > 0) refreshGamesList()
        }
    }

    fun deleteGame(chessGame: ChessGame) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            useCases.deleteChessGame.invoke(chessGame)
        }
    }

    fun getActivePlayerNumber(): Int =
        selectedGame?.getActivePlayerNumber() ?: 0

    fun setSelectedGame(chessGame: ChessGame?) {
        this.selectedGame = chessGame
        player1Moves.set(0)
        player2Moves.set(0)
        updateGameStartedObserver { GameState.NOT_STARTED }
    }

    fun switchPlayer(remainingTime: Long) {
        val previouslyActivePlayer = selectedGame?.getActivePlayerNumber()
        selectedGame?.switchPlayer(if (remainingTime == 0L) selectedGame!!.time else remainingTime)
        notifyObservers(previouslyActivePlayer ?: -1)
    }

    fun stopGame() = updateGameStartedObserver { selectedGame?.stop() }

    fun isGameResumed(): Boolean = selectedGame?.isGameResumed() == true

    fun pauseGame(playerTimeRemaining: Long?) =
        updateGameStartedObserver { selectedGame?.pause(playerTimeRemaining ?: 0L) }

    fun resumeGame() = updateGameStartedObserver {
        if (selectedGame?.isGameStarted() == false) selectedGame?.start()
        else selectedGame?.resume()
    }

    fun resetGame() = updateGameStartedObserver { selectedGame?.reset() }

    private fun refreshGamesList() {
        _mutableShouldUpdateList.postValue(selectedGame)
    }

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

    fun isEditMode() = selectedGame != null

    init {
        DaggerViewModelComponent
            .builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }
}
