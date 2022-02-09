package com.deluxe.chessclock.presentation.listener

import com.deluxe.core.data.ChessGame

interface OnChessGameClickedListener {
    fun onChessGameClick(chessGame: ChessGame)
    fun onCustomChessGameClick(chessGame: ChessGame)
}