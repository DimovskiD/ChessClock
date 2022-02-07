package com.deluxe.chessclock.framework.data.model

import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Clock
import com.deluxe.core.data.MINUTE

class BlitzChessIncrement : ChessGame("Blitz Increment", Clock(3 * MINUTE, 2))