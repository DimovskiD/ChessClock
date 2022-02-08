package com.deluxe.chessclock.framework.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.deluxe.chessclock.framework.data.dao.ChessGameDao
import com.deluxe.chessclock.framework.data.model.ChessGameEntity

@Database(entities = [ChessGameEntity::class], version = 1)
abstract class ChessGameDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "chess_games.db"

        private var instance: ChessGameDatabase? = null

        private fun create(context: Context): ChessGameDatabase =
            Room.databaseBuilder(context, ChessGameDatabase::class.java, DATABASE_NAME)
                .createFromAsset("database/chess_games.db")
                .build()


        fun getInstance(context: Context): ChessGameDatabase =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun chessGameDao(): ChessGameDao
}