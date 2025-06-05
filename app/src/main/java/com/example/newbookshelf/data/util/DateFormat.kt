package com.example.newbookshelf.data.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val TAG = "DateFormat"
object DateFormat {
    fun formatDateTime(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val dateTime = LocalDateTime.parse(inputDate, inputFormatter)

        val dayOfWeekKorean = when (dateTime.dayOfWeek.value) {
            1 -> "월"
            2 -> "화"
            3 -> "수"
            4 -> "목"
            5 -> "금"
            6 -> "토"
            7 -> "일"
            else -> ""
        }

        val outputFormatter = DateTimeFormatter.ofPattern("MM/dd", Locale.KOREA)
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA)

        return "${dateTime.format(outputFormatter)} ($dayOfWeekKorean) ${dateTime.format(timeFormatter)}"
    }

    fun isPast(targetTime: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val targetDateTime = LocalDateTime.parse(targetTime, formatter)
        val now = LocalDateTime.now()

        return targetDateTime.isBefore(now)
    }
}