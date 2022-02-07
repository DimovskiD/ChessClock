package com.deluxe.chessclock.framework.di

import com.deluxe.chessclock.framework.UseCases
import com.deluxe.core.repository.ChessRepository
import com.deluxe.core.usecase.DeleteChessGame
import com.deluxe.core.usecase.GetAllChessGames
import com.deluxe.core.usecase.GetChessGameById
import com.deluxe.core.usecase.InsertChessGame
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun getUseCases(chessRepository: ChessRepository) = UseCases(
        GetAllChessGames(chessRepository),
        GetChessGameById(chessRepository),
        InsertChessGame(chessRepository),
        DeleteChessGame(chessRepository)
    )
}