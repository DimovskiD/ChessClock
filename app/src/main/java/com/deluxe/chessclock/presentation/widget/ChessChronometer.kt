package com.deluxe.chessclock.presentation.widget

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer


class ChessChronometer : Chronometer {
    var isRunning = false
        private set

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    fun start(timeLeft : Long) {
        base = SystemClock.elapsedRealtime() + timeLeft
        start()
    }

    override fun start() {
        super.start()
        isRunning = true
    }

    override fun stop() {
        super.stop()
        isRunning = false
    }
}