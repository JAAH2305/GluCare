package com.glucare.ui.screens

import androidx.compose.ui.graphics.Color
import com.glucare.ui.theme.GlucoseColors
import java.text.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class GlucoseReading(
    val id: Long,
    val value: Int,
    val timestamp: Long,
    val tag: String? = null,
    val notes: String = "",
) {
    val status: GlucoseStatus
        get() = when {
            value < 70 -> GlucoseStatus.Low
            value <= 140 -> GlucoseStatus.Normal
            else -> GlucoseStatus.High
        }
}

enum class GlucoseStatus(val label: String) {
    Low("Low"), Normal("Normal"), High("High");

    internal val background: Color
        get() = when (this) {
            Low -> GlucoseColors.Blue
            Normal -> GlucoseColors.LightGreen
            High -> GlucoseColors.Pink
        }
}

internal fun readingDate(timestamp: Long): String =
    DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).format(Date(timestamp))

internal fun readingTime(timestamp: Long): String =
    DateFormat.getTimeInstance(DateFormat.SHORT, Locale.US).format(Date(timestamp))

internal fun readingDateTime(timestamp: Long): String =
    "${readingDate(timestamp)} • ${readingTime(timestamp)}"

internal fun cutoffDays(days: Int): Long = Calendar.getInstance().apply {
    add(Calendar.DAY_OF_YEAR, -days + 1)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}.timeInMillis

internal fun demoReadings(now: Long = System.currentTimeMillis()): List<GlucoseReading> {
    fun time(daysAgo: Int, hour: Int, minute: Int): Long = Calendar.getInstance().apply {
        timeInMillis = now
        add(Calendar.DAY_OF_YEAR, -daysAgo)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis.coerceAtMost(now)

    return listOf(
        GlucoseReading(1, 120, time(0, 8, 30), "Fasting", "Felt good, before breakfast"),
        GlucoseReading(2, 150, time(1, 19, 15), "After meal"),
        GlucoseReading(3, 90, time(1, 8, 0), "Before meal"),
        GlucoseReading(4, 180, time(2, 18, 30), "After meal"),
        GlucoseReading(5, 110, time(3, 9, 0), "Fasting"),
        GlucoseReading(6, 135, time(4, 11, 0)),
        GlucoseReading(7, 95, time(5, 9, 0)),
        GlucoseReading(8, 127, time(15, 8, 0)),
        GlucoseReading(9, 109, time(50, 8, 0)),
    )
}
