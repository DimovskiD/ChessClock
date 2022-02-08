package com.deluxe.chessclock.framework.data

import android.content.Context
import com.deluxe.chessclock.framework.data.model.ChessGameEntity
import com.deluxe.core.data.ChessGame
import com.deluxe.core.repository.ChessDataSource

class ChessGamesDataSource(context : Context) : ChessDataSource {

    private val chessGameDao = ChessGameDatabase.getInstance(context).chessGameDao()

    override suspend fun getAllChessGames(): List<ChessGame> = chessGameDao.getAllChessGames().map { it.toChessGame() }

    override suspend fun addChessGame(chessGame: ChessGame): Long = chessGameDao.addChessGameDetails(
        ChessGameEntity.fromChessGame(chessGame))

    override suspend fun getChessGameById(chessGameId: Long): ChessGame? = chessGameDao.getChessGameById(chessGameId)?.toChessGame()

    override suspend fun deleteChessGame(chessGame: ChessGame) = chessGameDao.deleteChessGame(
        ChessGameEntity.fromChessGame(chessGame))


}