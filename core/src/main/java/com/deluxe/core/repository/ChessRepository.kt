package com.deluxe.core.repository

import com.deluxe.core.data.ChessGame

class ChessRepository(private val chessDataSource: ChessDataSource) {

    fun getDefaultChessVariants() : List<ChessGame> = chessDataSource.getDefaultChessVariants()

}