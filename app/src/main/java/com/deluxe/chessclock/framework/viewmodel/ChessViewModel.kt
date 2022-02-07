package com.deluxe.chessclock.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.deluxe.chessclock.framework.UseCases
import com.deluxe.chessclock.framework.di.ApplicationModule
import com.deluxe.chessclock.framework.di.DaggerViewModelComponent
import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Resource
import javax.inject.Inject

class ChessViewModel (application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases : UseCases

    fun getChessGames() = liveData {
        emit(Resource.loading(null))
        emit(Resource.success(useCases.getAllChessGames()))
    }

    init {
        DaggerViewModelComponent
            .builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }
}