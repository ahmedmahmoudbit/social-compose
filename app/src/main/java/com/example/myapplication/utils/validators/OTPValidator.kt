package com.example.myapplication.utils.validators

import android.content.Context
import com.example.myapplication.R

object OTPValidator {
    
    fun validateOTP(otp: String?, context: Context): String? {
        return when {
            otp.isNullOrEmpty() -> context.getString(R.string.otp_is_required)
            otp.length != 6 -> context.getString(R.string.invalid_otp)
            !otp.all { it.isDigit() } -> context.getString(R.string.invalid_otp)
            else -> null
        }
    }
    
    fun isValidOTP(otp: String): Boolean {
        return otp.length == 6 && otp.all { it.isDigit() }
    }
}
