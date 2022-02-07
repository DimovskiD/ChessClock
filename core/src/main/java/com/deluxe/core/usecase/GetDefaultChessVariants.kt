package com.deluxe.core.usecase

import com.deluxe.core.data.ChessGame
import com.deluxe.core.repository.ChessRepository

class GetDefaultChessVariants(private val chessRepository: ChessRepository) {

    operator fun invoke() : List<ChessGame> = chessRepository.getDefaultChessVariants()

}