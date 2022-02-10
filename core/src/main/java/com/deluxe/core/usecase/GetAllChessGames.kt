package com.deluxe.core.usecase

import com.deluxe.core.data.ChessGame
import com.deluxe.core.repository.ChessRepository

class GetAllChessGames(private val chessRepository: ChessRepository) {

    suspend operator fun invoke() : List<ChessGame> = chessRepository.getAllChessGames()

}