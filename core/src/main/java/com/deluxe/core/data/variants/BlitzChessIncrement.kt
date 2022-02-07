package com.deluxe.core.data.variants

import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Clock
import com.deluxe.core.data.MINUTE

class BlitzChessIncrement : ChessGame(Clock(3 * MINUTE, 2))