package com.deluxe.core.repository

import com.deluxe.core.data.ChessGame

interface ChessDataSource {

    fun getDefaultChessVariants() : List<ChessGame>

}