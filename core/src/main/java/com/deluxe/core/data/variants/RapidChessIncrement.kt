package com.deluxe.core.data.variants

import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Clock
import com.deluxe.core.data.MINUTE

class RapidChessIncrement : ChessGame(Clock(10 * MINUTE, 10))