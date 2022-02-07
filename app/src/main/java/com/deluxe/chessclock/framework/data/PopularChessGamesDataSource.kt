package com.deluxe.chessclock.framework.data

import com.deluxe.chessclock.framework.data.model.*
import com.deluxe.core.data.ChessGame
import com.deluxe.core.repository.ChessDataSource

class PopularChessGamesDataSource : ChessDataSource {

    override fun getDefaultChessVariants(): List<ChessGame> = arrayListOf(
        BlitzChess(),
        BlitzChessIncrement(),
        BulletChess(),
        BulletChessIncrement(),
        ClassicalChess(),
        ClassicalChessIncrement(),
        RapidChess(),
        RapidChessIncrement()
    )
}