package com.example.myapplication.utils.resource

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplication.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.temporal.ChronoUnit

object Utils {
    var username:String = ""
    var currentTheme:String = ""

    fun imageToMultipart(uri: Uri, context: Context): MultipartBody.Part? {
        val file = File(context.cacheDir, "avatar.jpg")
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("avatar", file.name, requestBody)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTimeAgo(iso8601String: String, context: Context): String {
        return try {
            val createdAt = Instant.parse(iso8601String)
            val now = Instant.now()
            val diffSeconds = ChronoUnit.SECONDS.between(createdAt, now)

            when {
                diffSeconds < 60 -> context.getString(R.string.just_now)

                diffSeconds < 3600 -> {
                    val mins = diffSeconds / 60
                    val unit = if (mins == 1L) {
                        context.getString(R.string.unit_minute_singular)
                    } else {
                        context.getString(R.string.unit_minute_plural)
                    }
                    context.getString(R.string.time_ago, mins, unit)
                }

                diffSeconds < 86400 -> {
                    val hours = diffSeconds / 3600
                    val unit = if (hours == 1L) {
                        context.getString(R.string.unit_hour_singular)
                    } else {
                        context.getString(R.string.unit_hour_plural)
                    }
                    context.getString(R.string.time_ago, hours, unit)
                }

                diffSeconds < 604800 -> {
                    val days = diffSeconds / 86400
                    val unit = when {
                        days == 1L -> context.getString(R.string.unit_day_singular)
                        days == 2L && isArabic(context) -> context.getString(R.string.unit_day_dual)
                        else -> context.getString(R.string.unit_day_plural)
                    }
                    context.getString(R.string.time_ago, days, unit)
                }

                diffSeconds < 2592000 -> {
                    val weeks = diffSeconds / 604800
                    val unit = if (weeks == 1L) {
                        context.getString(R.string.unit_week_singular)
                    } else {
                        context.getString(R.string.unit_week_plural)
                    }
                    context.getString(R.string.time_ago, weeks, unit)
                }

                diffSeconds < 31536000 -> {
                    val months = diffSeconds / 2592000
                    val unit = when {
                        months == 1L -> context.getString(R.string.unit_month_singular)
                        months == 2L && isArabic(context) -> context.getString(R.string.unit_month_dual)
                        else -> context.getString(R.string.unit_month_plural)
                    }
                    context.getString(R.string.time_ago, months, unit)
                }

                else -> {
                    val years = diffSeconds / 31536000
                    val unit = when {
                        years == 1L -> context.getString(R.string.unit_year_singular)
                        years == 2L && isArabic(context) -> context.getString(R.string.unit_year_dual)
                        else -> context.getString(R.string.unit_year_plural)
                    }
                    context.getString(R.string.time_ago, years, unit)
                }
            }
        } catch (e: Exception) {
            context.getString(R.string.unknown_date)
        }
    }

    private fun isArabic(context: Context): Boolean {
        val locale = context.resources.configuration.locales[0] // API 33+
        return locale.language == "ar"
    }


}
