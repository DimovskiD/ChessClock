package com.deluxe.chessclock.framework.di

import android.app.Application
import com.deluxe.chessclock.framework.data.PopularChessGamesDataSource
import com.deluxe.core.repository.ChessRepository
import dagger.Module
import dagger.Provides

@Module
class ChessRepositoryModule {

    @Provides
    fun provideChessRepositoryModule() = ChessRepository(PopularChessGamesDataSource())

}