package com.deluxe.chessclock.framework.di

import android.app.Application
import com.deluxe.chessclock.framework.data.ChessGamesDataSource
import com.deluxe.core.repository.ChessRepository
import dagger.Module
import dagger.Provides

@Module
class ChessRepositoryModule {

    @Provides
    fun provideChessRepositoryModule(application : Application) = ChessRepository(ChessGamesDataSource(application))

}