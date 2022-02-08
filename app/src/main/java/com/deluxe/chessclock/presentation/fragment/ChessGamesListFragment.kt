package com.deluxe.chessclock.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deluxe.chessclock.databinding.FragmentListChessGamesBinding
import com.deluxe.chessclock.framework.viewmodel.ChessViewModel
import com.deluxe.chessclock.presentation.adapter.ChessGameAdapter
import com.deluxe.chessclock.presentation.listener.OnChessGameClickedListener
import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Status

class ChessGamesListFragment : Fragment(), OnChessGameClickedListener {

    private val binding : FragmentListChessGamesBinding by lazy { FragmentListChessGamesBinding.inflate(
        LayoutInflater.from(context)) }
    private val viewModel : ChessViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getChessGames().observe(viewLifecycleOwner) {
            if (it.status == Status.SUCCESS) {
                val adapter = ChessGameAdapter(it.data!!, this@ChessGamesListFragment)
                binding.chessGames.adapter = adapter
            }
        }

        binding.chessGames.layoutManager = LinearLayoutManager(context)
        binding.chessGames.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onChessGameClick(chessGame: ChessGame) {
        viewModel.setActiveGame(chessGame)
        navigate(ChessGamesListFragmentDirections.actionFragmentListChessGamesToFragmentChessGame())
    }

    private fun navigate(direction: NavDirections) {
        Navigation.findNavController(binding.root).navigate(direction)
    }


    override fun onPause() {
        super.onPause()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.show()
    }

}