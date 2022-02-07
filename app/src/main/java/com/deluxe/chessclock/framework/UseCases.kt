package com.deluxe.chessclock.framework

import com.deluxe.core.usecase.DeleteChessGame
import com.deluxe.core.usecase.GetAllChessGames
import com.deluxe.core.usecase.GetChessGameById
import com.deluxe.core.usecase.InsertChessGame

data class UseCases(
    val getAllChessGames: GetAllChessGames,
    val getChessGameById: GetChessGameById,
    val insertChessGame: InsertChessGame,
    val deleteChessGame: DeleteChessGame
)