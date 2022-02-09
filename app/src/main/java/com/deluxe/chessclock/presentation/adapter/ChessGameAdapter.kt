package com.deluxe.chessclock.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.deluxe.chessclock.R
import com.deluxe.chessclock.databinding.AddChessGameViewHolderBinding
import com.deluxe.chessclock.databinding.ChessGameViewHolderBinding
import com.deluxe.chessclock.databinding.GamePlaceholderViewHolderBinding
import com.deluxe.chessclock.framework.data.CircularLinkedList
import com.deluxe.chessclock.framework.data.model.AddChessGame
import com.deluxe.chessclock.framework.data.model.ChessGamePlaceholder
import com.deluxe.chessclock.presentation.listener.OnChessGameClickedListener
import com.deluxe.core.data.ChessGame

class ChessGameAdapter(
    private val chessGames: ArrayList<ChessGame>,
    private val chessGameClickedListener: OnChessGameClickedListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val colorsQueue =
        CircularLinkedList(listOf(R.color.white, R.color.black, R.color.black, R.color.white))

    private enum class ViewType { GAME, CUSTOM_GAME, GAME_PLACEHOLDER }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewType.CUSTOM_GAME.ordinal -> AddChessGameViewHolder(
                AddChessGameViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.GAME.ordinal -> ChessGameViewHolder(
                ChessGameViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ChessGamePlaceholderViewHolder(
                GamePlaceholderViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChessGameViewHolder -> holder.bind(chessGames[position])
            is AddChessGameViewHolder -> holder.bind(chessGames[position])
            is ChessGamePlaceholderViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        chessGames[position] is AddChessGame -> ViewType.CUSTOM_GAME.ordinal
        chessGames[position] is ChessGamePlaceholder -> ViewType.GAME_PLACEHOLDER.ordinal
        else -> ViewType.GAME.ordinal
    }

    override fun getItemCount(): Int = chessGames.size

    fun removeGame(chessGame: ChessGame) {
        val index = chessGames.indexOf(chessGame)
        if (index > -1) {
            chessGames.remove(chessGame)
            notifyItemRemoved(index) //todo handle remaining items color
        }
    }

    private fun getColorPair(context: Context): Pair<Int, Int> {
        val backgroundColorResId = colorsQueue.pop()
        val foregroundColor =
            context.getColor(if (backgroundColorResId == R.color.black) R.color.white else R.color.black)
        val backgroundColor = context.getColor(backgroundColorResId)
        return foregroundColor to backgroundColor
    }

    inner class ChessGameViewHolder(private val binding: ChessGameViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chessGame: ChessGame) {
            binding.root.setOnClickListener {
                toggleVisibility()
            }
            binding.start.setOnClickListener {
                chessGameClickedListener.onChessGameClick(chessGame)
                toggleVisibility()
            }
            binding.delete.setOnClickListener {
                chessGameClickedListener.onChessGameDelete(chessGame)
                toggleVisibility()
            }
            binding.nameOfTheGame.text = chessGame.toString()
            binding.increment.text = chessGame.increment.toString()
            binding.duration.text = chessGame.getDuration()

            val (foregroundColor, backgroundColor) = getColorPair(binding.root.context)

            binding.root.setBackgroundColor(backgroundColor)
            binding.nameOfTheGame.setTextColor(foregroundColor)
            binding.duration.setTextColor(foregroundColor)
            binding.increment.setTextColor(foregroundColor)
            binding.incrementIcon.setColorFilter(foregroundColor)
            binding.timeIcon.setColorFilter(foregroundColor)
        }

        private fun toggleVisibility() {
            binding.actionsContainer.visibility = if (binding.actionsContainer.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.informationContainer.visibility = if (binding.actionsContainer.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    inner class AddChessGameViewHolder(private val binding: AddChessGameViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chessGame: ChessGame) {
            binding.root.setOnClickListener {
                chessGameClickedListener.onCustomChessGameClick(
                    chessGame
                )
            }
            val (foregroundColor, backgroundColor) = getColorPair(binding.root.context)
            binding.root.setBackgroundColor(backgroundColor)
            binding.customGame.setTextColor(foregroundColor)
            binding.plusIcon.setColorFilter(foregroundColor)
        }
    }

    inner class ChessGamePlaceholderViewHolder(private val binding: GamePlaceholderViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val (_, backgroundColor) = getColorPair(binding.root.context)
            binding.root.setBackgroundColor(backgroundColor)
        }
    }
}