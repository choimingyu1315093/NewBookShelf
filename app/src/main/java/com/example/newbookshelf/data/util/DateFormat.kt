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
    fun convertToCustomFormat(inputDate: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(inputDate)

            val outputFormat = SimpleDateFormat("MM/dd (E) HH:mm", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("UTC")
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun isDatePast(inputTime: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val targetDate: Date = dateFormat.parse(inputTime) ?: return false

            val currentDate = Date()
            currentDate.after(targetDate)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun isPast(targetTime: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val targetDateTime = LocalDateTime.parse(targetTime, formatter)
        val now = LocalDateTime.now()

        return targetDateTime.isBefore(now)
    }
}