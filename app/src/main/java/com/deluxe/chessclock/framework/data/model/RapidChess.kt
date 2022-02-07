package com.deluxe.chessclock.framework.data.model

import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Clock
import com.deluxe.core.data.MINUTE

class RapidChess : ChessGame("Rapid", Clock(10 * MINUTE, 0))