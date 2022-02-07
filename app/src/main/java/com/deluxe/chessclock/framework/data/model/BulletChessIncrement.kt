package com.deluxe.chessclock.framework.data.model

import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Clock
import com.deluxe.core.data.MINUTE

class BulletChessIncrement : ChessGame("Bullet Increment", Clock(2 * MINUTE, 1))