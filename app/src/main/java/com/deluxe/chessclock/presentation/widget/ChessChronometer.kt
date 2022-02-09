package com.deluxe.chessclock.presentation.widget

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer
import com.deluxe.chessclock.presentation.listener.OnTimeExpiredListener
import com.deluxe.core.data.Players


class ChessChronometer : Chronometer, Chronometer.OnChronometerTickListener {

    private var isRunning = false
    private var onTimeExpiredListener : OnTimeExpiredListener? = null
    private var winner : Players? = null

    constructor(context: Context?) : super(context) { init() }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { init() }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) { init() }

    private fun init() {
        onChronometerTickListener = this
    }


    fun start(timeLeft : Long) {
        base = SystemClock.elapsedRealtime() + timeLeft
        isRunning = true
        start()
    }


    fun stop(timeLeft: Long) {
        base = SystemClock.elapsedRealtime() + timeLeft
        invalidate()
        isRunning = false
        stop()
    }

    override fun onChronometerTick(chronometer: Chronometer?) {
        if( chronometer?.text.toString() == "00:00") {
            stop()
            onTimeExpiredListener?.onTimeExpired(winner!!)
        }
    }

    fun bindWinner(player: Players, onTimeExpiredListener: OnTimeExpiredListener) {
        this.onTimeExpiredListener = onTimeExpiredListener
        winner = player
    }
}