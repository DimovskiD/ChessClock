package com.deluxe.core.usecase

import com.deluxe.core.data.ChessGame
import com.deluxe.core.repository.ChessRepository

class UpsertChessGame(private val chessRepository: ChessRepository) {

    suspend operator fun invoke(chessGame: ChessGame) : Long = chessRepository.addChessGame(chessGame)

}