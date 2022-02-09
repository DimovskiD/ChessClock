package com.deluxe.chessclock.presentation.listener

import com.deluxe.core.data.Players

interface OnTimeExpiredListener {
    fun onTimeExpired(winner: Players)
}