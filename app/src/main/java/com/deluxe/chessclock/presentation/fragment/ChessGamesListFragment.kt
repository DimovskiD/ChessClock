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
import com.deluxe.chessclock.presentation.listener.OnChessGameClickedListener
import com.deluxe.chessclock.presentation.util.navigateSafely
import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Status

class ChessGamesListFragment : Fragment(), OnChessGameClickedListener {

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
        binding.chessGames.layoutManager = GridLayoutManager(context,2)
    }

    override fun onChessGameClick(chessGame: ChessGame) {
        viewModel.setActiveGame(chessGame)
        navigate(ChessGamesListFragmentDirections.actionFragmentListChessGamesToFragmentChessGame())
    }

    override fun onCustomChessGameClick(chessGame: ChessGame) {
        navigate(ChessGamesListFragmentDirections.actionFragmentListChessGamesToStartCustomChessGameFragment())
    }

    override fun onChessGameDelete(chessGame: ChessGame) {
        viewModel.deleteGame(chessGame)
        adapter?.removeGame(chessGame)
    }

    private fun navigate(direction: NavDirections) = navigateSafely(
        direction,
        R.id.fragmentListChessGames
    )


}