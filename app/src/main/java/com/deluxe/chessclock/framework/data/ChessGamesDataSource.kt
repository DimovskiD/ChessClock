package com.deluxe.chessclock.framework.data

import android.content.Context
import com.deluxe.chessclock.framework.data.model.ChessGameEntity
import com.deluxe.core.data.ChessGame
import com.deluxe.core.repository.ChessDataSource

class ChessGamesDataSource(context : Context) : ChessDataSource {

    private val chessGameDao = ChessGameDatabase.getInstance(context).chessGameDao()

    override suspend fun getAllChessGames(): List<ChessGame> = chessGameDao.getAllChessGames().map { it.toChessGame() }

    override suspend fun addContactDetails(chessGame: ChessGame): Long = chessGameDao.addContactDetails(
        ChessGameEntity.fromChessGame(chessGame))

    override suspend fun getContactDetailsById(chessGameId: Long): ChessGame? = chessGameDao.getContactDetailsById(chessGameId)?.toChessGame()

    override suspend fun deleteChessGame(chessGame: ChessGame) = chessGameDao.deleteChessGame(
        ChessGameEntity.fromChessGame(chessGame))


}