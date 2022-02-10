package com.deluxe.chessclock.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deluxe.chessclock.R
import com.deluxe.chessclock.databinding.AddChessGameViewHolderBinding
import com.deluxe.chessclock.databinding.ChessGameViewHolderBinding
import com.deluxe.chessclock.databinding.GamePlaceholderViewHolderBinding
import com.deluxe.chessclock.framework.data.CircularLinkedList
import com.deluxe.chessclock.framework.data.model.AddChessGame
import com.deluxe.chessclock.framework.data.model.ChessGamePlaceholder
import com.deluxe.chessclock.presentation.listener.OnChessGameActionListener
import com.deluxe.core.data.ChessGame

class ChessGameAdapter(
    private val chessGames: ArrayList<ChessGame>,
    private val chessGameActionListener: OnChessGameActionListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val colorsQueue =
        CircularLinkedList(listOf(R.color.white, R.color.black, R.color.black, R.color.white))
    private val positionToColorMap = hashMapOf<Int, Pair<Int, Int>>()
    private var actionsShownPosition = -1

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
            is ChessGameViewHolder -> holder.bind(chessGames[position], position)
            is AddChessGameViewHolder -> holder.bind(position)
            is ChessGamePlaceholderViewHolder -> holder.bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        chessGames[position] is AddChessGame -> ViewType.CUSTOM_GAME.ordinal
        chessGames[position] is ChessGamePlaceholder -> ViewType.GAME_PLACEHOLDER.ordinal
        else -> ViewType.GAME.ordinal
    }

    override fun getItemCount(): Int = chessGames.size

    fun itemChanged(selectedGame: ChessGame) {
        val index = chessGames.indexOf(chessGames.first { it.id == selectedGame.id })
        chessGames[index] = selectedGame
        notifyItemChanged(index)
    }

    fun removeGame(chessGame: ChessGame) {
        val index = chessGames.indexOf(chessGame)
        if (index > -1) {
            chessGames.remove(chessGame)
            if (shouldAddPlaceholder()) chessGames.add(ChessGamePlaceholder())
            else removePlaceholder()
            notifyItemRangeChanged(index, chessGames.size - 1)
        }
    }

    private fun removePlaceholder() {
        chessGames.firstOrNull { it is ChessGamePlaceholder }?.let { placeholder ->
            chessGames.remove(placeholder)
        }
    }

    private fun shouldAddPlaceholder(): Boolean = chessGames.size % 2 == 1

    private fun getColorPair(context: Context, position: Int): Pair<Int, Int> {
        if (positionToColorMap[position] != null) return positionToColorMap[position]!!
        val backgroundColorResId = colorsQueue.pop()
        val foregroundColor =
            context.getColor(if (backgroundColorResId == R.color.black) R.color.white else R.color.black)
        val backgroundColor = context.getColor(backgroundColorResId)
        positionToColorMap[position] = foregroundColor to backgroundColor
        return positionToColorMap[position]!!
    }

    inner class ChessGameViewHolder(private val binding: ChessGameViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chessGame: ChessGame, position: Int) {
            setClickListeners(chessGame, position)
            setUpUI(chessGame, position)
        }

        private fun setUpUI(chessGame: ChessGame, position: Int) {
            binding.actionsContainer.visibility = View.GONE
            binding.informationContainer.visibility = View.VISIBLE
            binding.nameOfTheGame.text = chessGame.toString()
            binding.increment.text = chessGame.increment.toString()
            binding.duration.text = chessGame.getDuration()

            val (foregroundColor, backgroundColor) = getColorPair(binding.root.context, position)
            binding.root.setBackgroundColor(backgroundColor)
            binding.nameOfTheGame.setTextColor(foregroundColor)
            binding.duration.setTextColor(foregroundColor)
            binding.increment.setTextColor(foregroundColor)
            binding.incrementIcon.setColorFilter(foregroundColor)
            binding.timeIcon.setColorFilter(foregroundColor)
        }

        private fun setClickListeners(chessGame: ChessGame, position: Int) {
            binding.root.setOnClickListener {
                toggleVisibility(position)
            }
            binding.start.setOnClickListener {
                chessGameActionListener.onStartChessGame(chessGame)
                toggleVisibility(position)
            }
            binding.delete.setOnClickListener {
                actionsShownPosition = -1
                chessGameActionListener.onChessGameDelete(chessGame)
                toggleVisibility(position)
            }
            binding.edit.setOnClickListener {
                chessGameActionListener.onEditChessGame(chessGame)
                toggleVisibility(position)
            }
        }

        private fun toggleVisibility(position: Int) {
            if (actionsShownPosition != -1 && actionsShownPosition != position)
                notifyItemChanged(actionsShownPosition)
            binding.actionsContainer.visibility =
                if (binding.actionsContainer.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.informationContainer.visibility =
                if (binding.actionsContainer.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            if (binding.actionsContainer.visibility == View.VISIBLE)
                actionsShownPosition = position
        }
    }

    inner class AddChessGameViewHolder(private val binding: AddChessGameViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.root.setOnClickListener {
                chessGameActionListener.onAddChessGame()
            }
            val (foregroundColor, backgroundColor) = getColorPair(binding.root.context, position)
            binding.root.setBackgroundColor(backgroundColor)
            binding.customGame.setTextColor(foregroundColor)
            binding.plusIcon.setColorFilter(foregroundColor)
        }
    }

    inner class ChessGamePlaceholderViewHolder(private val binding: GamePlaceholderViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val (_, backgroundColor) = getColorPair(binding.root.context, position)
            binding.root.setBackgroundColor(backgroundColor)
        }
    }
}