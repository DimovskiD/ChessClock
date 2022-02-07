package com.deluxe.core.data.variants

import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Clock
import com.deluxe.core.data.MINUTE

class BulletChessIncrement : ChessGame(Clock(2 * MINUTE, 1))