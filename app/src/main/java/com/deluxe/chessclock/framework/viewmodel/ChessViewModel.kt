package com.deluxe.chessclock.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.deluxe.chessclock.framework.UseCases
import com.deluxe.chessclock.framework.di.ApplicationModule
import com.deluxe.chessclock.framework.di.DaggerViewModelComponent
import com.deluxe.core.data.ChessGame
import javax.inject.Inject

class ChessViewModel (application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases : UseCases

    fun getChessGames(): List<ChessGame> {
        return useCases.getDefaultChessVariants()
    }

    init {
        DaggerViewModelComponent
            .builder()
            .build()
            .inject(this)
    }
}