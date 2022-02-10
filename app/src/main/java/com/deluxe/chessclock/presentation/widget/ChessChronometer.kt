package com.deluxe.chessclock.presentation.widget

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import com.deluxe.chessclock.presentation.listener.OnTimeExpiredListener
import com.deluxe.core.data.Players
import com.deluxe.core.formatTime


class ChessChronometer : androidx.appcompat.widget.AppCompatTextView {

    private lateinit var winner : Players
    private lateinit var countDownTimer: CountDownTimer
    private var milliSecondsLeft : Long = -1L

    private var onTimeExpiredListener : OnTimeExpiredListener? = null
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    fun init(duration : Long) {
        milliSecondsLeft = duration
    }

    fun start() {
        countDownTimer = ChessCountDownTimer(milliSecondsLeft)
        countDownTimer.start()
    }

    fun stop(timeLeft : Long) {
        milliSecondsLeft = timeLeft
        text = timeLeft.formatTime(true)
        countDownTimer.cancel()
    }

    fun bindWinner(player: Players, onTimeExpiredListener: OnTimeExpiredListener) {
        this.onTimeExpiredListener = onTimeExpiredListener
        winner = player
    }

    fun getTimeLeft(): Long {
        return milliSecondsLeft
    }

    inner class ChessCountDownTimer(milliSecondsLeft: Long) :
        CountDownTimer(milliSecondsLeft, 100) {
        override fun onTick(milliSecondsLeft: Long) {
            this@ChessChronometer.milliSecondsLeft = milliSecondsLeft
            text = milliSecondsLeft.formatTime(true)
        }

        override fun onFinish() {
            onTimeExpiredListener?.onTimeExpired(winner)
        }
    }
}