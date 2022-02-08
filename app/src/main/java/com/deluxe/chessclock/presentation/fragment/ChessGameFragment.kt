package com.deluxe.chessclock.presentation.fragment

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.deluxe.chessclock.databinding.FragmentChessGameBinding
import com.deluxe.core.data.Players
import com.deluxe.chessclock.framework.viewmodel.ChessViewModel

class ChessGameFragment : Fragment() {

    private val playerTimerMap by lazy {
        hashMapOf(
            Players.PLAYER_ONE.playerNumber to binding.playerOneTime,
            Players.PLAYER_TWO.playerNumber to binding.playerTwoTime
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
        binding.root.setOnClickListener {
            playerTimerMap[viewModel.getActivePlayerNumber()]?.stop()
            viewModel.performAction(calculateRemainingTime())
            playerTimerMap[viewModel.getActivePlayerNumber()]?.start(
                viewModel.activeGame?.getActivePlayerRemainingTime() ?: 0L
            )
        }
    }

    private fun calculateRemainingTime(): Long? =
        (playerTimerMap[viewModel.getActivePlayerNumber()]?.base?.minus(SystemClock.elapsedRealtime()))?.div(
            1000
        )
}