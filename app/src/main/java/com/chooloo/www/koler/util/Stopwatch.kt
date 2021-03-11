package com.chooloo.www.koler.util

import java.util.*

/**
 * Just a simple stopwatch class
 */
class Stopwatch {
    private var startTime: Long = 0
    private var stopTime: Long = 0
    private var isRunning = false

    val elapsedTime: Long
        get() = if (isRunning) System.currentTimeMillis() - startTime else stopTime - startTime

    val elapsedTimeSecs: Long
        get() = if (isRunning) (System.currentTimeMillis() - startTime) / 1000 else (stopTime - startTime) / 1000

    fun start() {
        startTime = System.currentTimeMillis()
        isRunning = true
    }

    fun stop() {
        stopTime = System.currentTimeMillis()
        isRunning = false
    }

    val stringTime: String
        get() {
            val currentTime = elapsedTime
            var seconds = currentTime.toInt() / 1000
            var minutes = seconds / 60
            val hours = minutes / 60
            seconds -= minutes * 60
            minutes -= hours * 60
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        }
}