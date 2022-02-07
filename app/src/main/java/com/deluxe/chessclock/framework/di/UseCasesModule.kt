package com.deluxe.chessclock.framework.di

import com.deluxe.chessclock.framework.UseCases
import com.deluxe.core.repository.ChessRepository
import com.deluxe.core.usecase.GetDefaultChessVariants
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun getUseCases(chessRepository: ChessRepository) = UseCases(GetDefaultChessVariants(chessRepository))
}