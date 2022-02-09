package com.deluxe.chessclock.presentation.fragment

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.deluxe.chessclock.R
import com.deluxe.chessclock.databinding.FragmentChessGameBinding
import com.deluxe.chessclock.framework.viewmodel.ChessViewModel
import com.deluxe.chessclock.presentation.listener.OnTimeExpiredListener
import com.deluxe.chessclock.presentation.widget.ChessChronometer
import com.deluxe.core.data.Players


class ChessGameFragment : Fragment(), OnTimeExpiredListener {

    private val playerTimerMap by lazy {
        hashMapOf(
            Players.PLAYER_ONE.playerNumber to binding.playerOneTime.apply { this.bindWinner(Players.PLAYER_TWO, this@ChessGameFragment) },
            Players.PLAYER_TWO.playerNumber to binding.playerTwoTime.apply { this.bindWinner(Players.PLAYER_ONE, this@ChessGameFragment) }
        )
    }

    private val binding: FragmentChessGameBinding by lazy {
        FragmentChessGameBinding.inflate(
            LayoutInflater.from(context)
        )
    }
    private val viewModel: ChessViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            switchPlayer()
        }

        binding.start.setOnClickListener {
            handleStartClicked()
        }

        binding.reset.setOnClickListener {
            resetGame()
        }
    }

    private fun handleStartClicked() {
        if (viewModel.isGameResumed()) pauseGame()
        else {
            viewModel.resumeGame()
            startCounter()
        }
    }

    private fun switchPlayer() {
        if (viewModel.isGameResumed()) {
            val activePlayerBeforeSwitch = viewModel.getActivePlayerNumber()
            viewModel.switchPlayer(calculateRemainingTime())
            getChronometerForPlayer(activePlayerBeforeSwitch)?.stop(getTimeLeftForPlayer(false))
            startCounter()
        }
    }

    override fun onPause() {
        super.onPause()
        pauseGame()
    }

    override fun onTimeExpired(winner: Players) {
        finishGame(winner)
    }

    private fun pauseGame() {
        getChronometerForPlayer(viewModel.getActivePlayerNumber())?.stop()
        viewModel.pauseGame(calculateRemainingTime())
    }

    private fun resetGame() {
        getChronometerForPlayer(viewModel.getActivePlayerNumber())?.stop()
        viewModel.resetGame()
        binding.invalidateAll()
    }

    private fun startCounter() {
        playerTimerMap[viewModel.getActivePlayerNumber()]?.start(getTimeLeftForPlayer(true))
    }

    private fun getTimeLeftForPlayer(isActive: Boolean): Long =
        viewModel.activeGame?.getPlayer(isActive)?.getTimeInMillis() ?: 0L


    private fun calculateRemainingTime(): Long =
        (playerTimerMap[viewModel.getActivePlayerNumber()]?.base?.minus(SystemClock.elapsedRealtime()))?.div(
            1000
        ) ?: 0L

    private fun getChronometerForPlayer(playerNumber: Int): ChessChronometer? =
        playerTimerMap[playerNumber]

    private fun finishGame(winner : Players) {
        Toast.makeText(context, getString(R.string.game_over, winner.playerNumber), Toast.LENGTH_LONG).show()
        viewModel.stopGame()
    }

}