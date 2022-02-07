package com.deluxe.chessclock.framework.di

import com.deluxe.chessclock.framework.viewmodel.ChessViewModel
import dagger.Component

@Component(modules = [ChessRepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(contactsViewModel: ChessViewModel)
}