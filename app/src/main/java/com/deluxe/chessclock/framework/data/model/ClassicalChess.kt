package com.deluxe.chessclock.framework.data.model

import com.deluxe.core.data.ChessGame
import com.deluxe.core.data.Clock
import com.deluxe.core.data.MINUTE

class ClassicalChess : ChessGame("Classical", Clock(15 * MINUTE, 0))