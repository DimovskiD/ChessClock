package com.deluxe.core.repository

import com.deluxe.core.data.ChessGame

interface ChessDataSource {

    suspend fun getAllChessGames() : List<ChessGame>

    suspend fun addChessGame(chessGame: ChessGame) : Long

    suspend fun getChessGameById(chessGameId : Long) : ChessGame?

    suspend fun deleteChessGame(chessGame: ChessGame)
}