package com.deluxe.chessclock.framework.data.dao

import androidx.room.*
import com.deluxe.chessclock.framework.data.model.ChessGameEntity

@Dao
interface ChessGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChessGameDetails(chessGameEntity: ChessGameEntity) : Long

    @Query("SELECT * FROM chess_game_entity")
    suspend fun getAllChessGames() : List<ChessGameEntity>

    @Delete
    suspend fun deleteChessGame(chessGameEntity: ChessGameEntity)

}
