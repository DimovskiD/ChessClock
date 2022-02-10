package com.deluxe.chessclock.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.GridLayoutManager
import com.deluxe.chessclock.R
import com.deluxe.chessclock.databinding.FragmentListChessGamesBinding
import com.deluxe.chessclock.framework.viewmodel.ChessViewModel
import com.deluxe.chessclock.presentation.adapter.ChessGameAdapter
import com.deluxe.chessclock.presentation.listener.OnChessGameActionListener
import com.deluxe.chessclock.presentation.util.navigateSafely
import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Status

class ChessGamesListFragment : Fragment(), OnChessGameActionListener {

    private val binding : FragmentListChessGamesBinding by lazy { FragmentListChessGamesBinding.inflate(
        LayoutInflater.from(context)) }
    private val viewModel : ChessViewModel by activityViewModels()
    private var adapter : ChessGameAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getChessGames().observe(viewLifecycleOwner) {
            if (it.status == Status.SUCCESS && it.data != null) {
                adapter = ChessGameAdapter(it.data!!, this@ChessGamesListFragment)
                binding.chessGames.adapter = adapter
            }
        }

        viewModel.shouldUpdateList.observe(viewLifecycleOwner) {
            if (it != null) adapter?.itemChanged(it)
        }
        binding.chessGames.layoutManager = GridLayoutManager(context,2)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSelectedGame(null)
    }

    override fun onStartChessGame(chessGame: ChessGame) {
        viewModel.setSelectedGame(chessGame)
        navigate(ChessGamesListFragmentDirections.actionFragmentListChessGamesToFragmentChessGame())
    }

    override fun onAddChessGame() {
        viewModel.setSelectedGame(null)
        navigate(ChessGamesListFragmentDirections.actionFragmentListChessGamesToStartCustomChessGameFragment())
    }

    override fun onChessGameDelete(chessGame: ChessGame) {
        viewModel.deleteGame(chessGame)
        adapter?.removeGame(chessGame)
    }

    override fun onEditChessGame(chessGame: ChessGame) {
        viewModel.setSelectedGame(chessGame)
        navigate(ChessGamesListFragmentDirections.actionFragmentListChessGamesToStartCustomChessGameFragment())
    }

    private fun navigate(direction: NavDirections) = navigateSafely(
        direction,
        R.id.fragmentListChessGames
    )


}