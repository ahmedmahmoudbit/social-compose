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
    fun formatTimeAgo(iso8601String: String,context: Context): String {
        return try {
            val createdAt = Instant.parse(iso8601String)
            val now = Instant.now()
            val diffSeconds = ChronoUnit.SECONDS.between(createdAt, now)

            when {
                diffSeconds < 60 -> context.getString(R.string.just_now)
                diffSeconds < 3600 -> {
                    val mins = diffSeconds / 60
                    context.getString(R.string.minute_ago, mins, if (mins == 1L) "" else context.getString(R.string.s))
                }
                diffSeconds < 86400 -> {
                    val hours = diffSeconds / 3600
                    context.getString(R.string.hour_ago, hours, if (hours == 1L) "" else context.getString(R.string.s))
                }
                diffSeconds < 604800 -> {
                    val days = diffSeconds / 86400
                    context.getString(
                        R.string.day_ago,
                        days,
                        if (days == 1L) "" else context.getString(R.string.s)
                    )
                }
                diffSeconds < 2592000 -> {
                    val weeks = diffSeconds / 604800
                    "$weeks week${if (weeks == 1L) "" else context.getString(R.string.s)} ago"
                }
                diffSeconds < 31536000 -> {
                    val months = diffSeconds / 2592000
                    context.getString(R.string.month_ago, months, if (months == 1L) "" else context.getString(R.string.s))
                }
                else -> {
                    val years = diffSeconds / 31536000
                    "$years year${if (years == 1L) "" else context.getString(R.string.s)} ago"
                }            }
        } catch (e: Exception) {
            "تاريخ غير معروف"
        }
    }
}
