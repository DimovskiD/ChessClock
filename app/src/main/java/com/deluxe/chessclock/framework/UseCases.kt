package com.deluxe.chessclock.framework

import com.deluxe.core.usecase.DeleteChessGame
import com.deluxe.core.usecase.GetAllChessGames
import com.deluxe.core.usecase.UpsertChessGame

data class UseCases(
    val getAllChessGames: GetAllChessGames,
    val upsertChessGame: UpsertChessGame,
    val deleteChessGame: DeleteChessGame
)