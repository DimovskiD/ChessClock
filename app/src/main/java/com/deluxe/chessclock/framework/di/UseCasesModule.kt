package com.deluxe.chessclock.framework.di

import com.deluxe.chessclock.framework.UseCases
import com.deluxe.core.repository.ChessRepository
import com.deluxe.core.usecase.DeleteChessGame
import com.deluxe.core.usecase.GetAllChessGames
import com.deluxe.core.usecase.UpsertChessGame
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun getUseCases(chessRepository: ChessRepository) = UseCases(
        GetAllChessGames(chessRepository),
        UpsertChessGame(chessRepository),
        DeleteChessGame(chessRepository)
    )
}