package com.example.newbookshelf.data.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateFormat {
    fun convertToCustomFormat(inputDate: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(inputDate)

            val outputFormat = SimpleDateFormat("MM/dd (E) HH:mm", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun isDatePast(inputDate: String): Boolean {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(inputDate)

            val currentTime = Date()
            date.before(currentTime)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}