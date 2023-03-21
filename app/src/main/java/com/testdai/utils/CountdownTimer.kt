package com.testdai.utils

import android.os.CountDownTimer

class CountdownTimer(
    private val totalTime: Long,
    private val tickMs: (Long) -> Unit
) {

    private var timer: CountDownTimer? = null

    fun start() {
        timer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisecs: Long) {
                tickMs(millisecs)
            }

            override fun onFinish() {
                tickMs(0L)
            }
        }
        timer?.start()
    }

    fun stop() {
        timer?.cancel()
        timer = null
    }

}