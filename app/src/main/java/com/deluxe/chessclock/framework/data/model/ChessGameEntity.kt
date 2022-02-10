package com.deluxe.chessclock.framework.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deluxe.core.data.ChessGame

@Entity(tableName = "chess_game_entity")
data class ChessGameEntity(
    val name: String,
    val time: Long,
    val increment: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) {

    companion object {
        fun fromChessGame(chessGame: ChessGame): ChessGameEntity = if (chessGame.id != -1L)
            ChessGameEntity(
                chessGame.name, chessGame.time, chessGame.increment, chessGame.id
            ) else
            ChessGameEntity(
                chessGame.name, chessGame.time, chessGame.increment
            )
    }

    fun toChessGame() = ChessGame(name, time, increment, id)
}