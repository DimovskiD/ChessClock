package com.deluxe.core.repository

import com.deluxe.core.data.ChessGame

class ChessRepository(private val chessDataSource: ChessDataSource) {

    suspend fun getAllChessGames() : List<ChessGame> = chessDataSource.getAllChessGames()

    suspend fun addChessGame(chessGameEntity: ChessGame) : Long = chessDataSource.addChessGame(chessGameEntity)

    suspend fun deleteChessGame(chessGameEntity: ChessGame) = chessDataSource.deleteChessGame(chessGameEntity)
}