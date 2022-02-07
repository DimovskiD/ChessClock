package com.deluxe.chessclock.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deluxe.chessclock.databinding.ChessGameViewHolderBinding
import com.deluxe.core.data.ChessGame

class ChessGameAdapter(private val chessGames : List<ChessGame>) : RecyclerView.Adapter<ChessGameAdapter.ChessGameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChessGameViewHolder =
        ChessGameViewHolder(ChessGameViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ChessGameViewHolder, position: Int) {
        holder.bind(chessGames[position])
    }

    override fun getItemCount(): Int = chessGames.size

    inner class ChessGameViewHolder(private val binding: ChessGameViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chessGame: ChessGame) {
            binding.nameOfTheGame.text = chessGame.toString()
            binding.increment.text = chessGame.getIncrement().toString()
            binding.duration.text = chessGame.getDuration()
        }
    }
}