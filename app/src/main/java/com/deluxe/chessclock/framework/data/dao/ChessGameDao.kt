package com.deluxe.chessclock.framework.data.dao

import androidx.room.*
import com.deluxe.chessclock.framework.data.model.ChessGameEntity

@Dao
interface ChessGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContactDetails(chessGameEntity: ChessGameEntity) : Long

    @Query("SELECT * FROM chess_game_entity")
    suspend fun getAllChessGames() : List<ChessGameEntity>

    @Query("SELECT * FROM chess_game_entity WHERE id = :chessGameId")
    suspend fun getContactDetailsById(chessGameId : Long) : ChessGameEntity?

    @Delete
    suspend fun deleteChessGame(chessGameEntity: ChessGameEntity)

}
