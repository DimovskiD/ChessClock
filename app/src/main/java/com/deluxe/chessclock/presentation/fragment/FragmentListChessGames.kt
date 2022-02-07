package com.deluxe.chessclock.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deluxe.chessclock.databinding.FragmentListChessGamesBinding
import com.deluxe.chessclock.framework.viewmodel.ChessViewModel
import com.deluxe.chessclock.presentation.adapter.ChessGameAdapter
import com.deluxe.core.data.Status

class FragmentListChessGames : Fragment(){

    private val binding : FragmentListChessGamesBinding by lazy { FragmentListChessGamesBinding.inflate(
        LayoutInflater.from(context)) }
    private val viewModel : ChessViewModel by lazy { ViewModelProvider(this).get(
        ChessViewModel::class.java) }

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
                val adapter = ChessGameAdapter(it.data!!)
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
}