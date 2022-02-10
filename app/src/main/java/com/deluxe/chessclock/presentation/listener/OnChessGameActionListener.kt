package com.deluxe.chessclock.presentation.listener

import com.deluxe.core.data.ChessGame

interface OnChessGameActionListener {
    fun onStartChessGame(chessGame: ChessGame)
    fun onChessGameDelete(chessGame: ChessGame)
    fun onEditChessGame(chessGame: ChessGame)
    fun onAddChessGame()
}