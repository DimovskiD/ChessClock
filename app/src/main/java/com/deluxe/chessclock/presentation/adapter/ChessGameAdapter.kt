package com.deluxe.chessclock.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deluxe.chessclock.R
import com.deluxe.chessclock.databinding.ChessGameViewHolderBinding
import com.deluxe.chessclock.framework.data.CircularLinkedList
import com.deluxe.chessclock.presentation.listener.OnChessGameClickedListener
import com.deluxe.core.data.ChessGame
import java.util.*

class ChessGameAdapter(private val chessGames : List<ChessGame>, private val chessGameClickedListener: OnChessGameClickedListener) : RecyclerView.Adapter<ChessGameAdapter.ChessGameViewHolder>() {

    private val colorsQueue = CircularLinkedList(listOf(R.color.white, R.color.black, R.color.black, R.color.white))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChessGameViewHolder =
        ChessGameViewHolder(ChessGameViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ChessGameViewHolder, position: Int) {
        holder.bind(chessGames[position])
    }

    override fun getItemCount(): Int = chessGames.size

    inner class ChessGameViewHolder(private val binding: ChessGameViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chessGame: ChessGame) {
            binding.root.setOnClickListener { chessGameClickedListener.onChessGameClick(chessGame) }
            binding.nameOfTheGame.text = chessGame.toString()
            binding.increment.text = chessGame.increment.toString()
            binding.duration.text = chessGame.getDuration()
            val backgroundColorResId = colorsQueue.pop()
            val foregroundColor = binding.root.context.getColor(if (backgroundColorResId == R.color.black) R.color.white else R.color.black)
            val backgroundColor = binding.root.context.getColor(backgroundColorResId)

            binding.root.setBackgroundColor(backgroundColor)
            binding.nameOfTheGame.setTextColor(foregroundColor)
            binding.duration.setTextColor(foregroundColor)
            binding.increment.setTextColor(foregroundColor)
            binding.incrementIcon.setColorFilter(foregroundColor)
            binding.timeIcon.setColorFilter(foregroundColor)

        }
    }
}