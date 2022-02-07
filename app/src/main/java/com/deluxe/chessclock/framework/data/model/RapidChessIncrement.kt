package com.deluxe.chessclock.framework.data.model

import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Clock
import com.deluxe.core.data.MINUTE

class RapidChessIncrement : ChessGame("Rapid Increment", Clock(10 * MINUTE, 10))