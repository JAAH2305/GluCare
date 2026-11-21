package com.glucare.ui.screens

data class ActivityItem(
    val id: Long,
    val name: String,
    val icon: String,
    val durationMinutes: Int,
    val calories: Int,
    val steps: Int,
    val heartRate: Int? = null,
    val dateLabel: String,
    val fullDateSubtitle: String = "",
    val time: String,
    val notes: String = ""
)

