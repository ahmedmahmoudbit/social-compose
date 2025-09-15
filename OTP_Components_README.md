# OTP (PIN) Components for Android Kotlin Compose

This documentation explains how to use the OTP/PIN input components that have been created for your Android Kotlin Compose project.

## Components Created

### 1. OTPInput
A reusable OTP input component that displays individual boxes for each digit.

**Features:**
- Customizable number of digits (default: 6)
- Automatic focus management
- Haptic feedback
- Error state handling
- Auto-fill support
- Keyboard handling (NumberPad)

**Usage:**
```kotlin
var otpText by remember { mutableStateOf("") }

OTPInput(
    otpText = otpText,
    otpCount = 6,
    onOtpTextChange = { value, isComplete ->
        otpText = value
        if (isComplete) {
            // Handle completed OTP
        }
    },
    errorMessage = null // or error string
)
```

### 2. OTPBottomSheet
A modal bottom sheet that contains the OTP input with verification functionality.

**Features:**
- Modal bottom sheet presentation
- Email address display
- Auto-verification when OTP is complete
- Resend functionality with countdown timer
- Error handling
- Loading states

**Usage:**
```kotlin
var showOTPBottomSheet by remember { mutableStateOf(false) }

OTPBottomSheet(
    isVisible = showOTPBottomSheet,
    onDismiss = { showOTPBottomSheet = false },
    onOTPVerified = { otpCode ->
        // Handle OTP verification
    },
    email = "user@example.com",
    isLoading = false,
    errorMessage = null
)
```

### 3. OTPValidator
Utility class for OTP validation.

**Methods:**
```kotlin
// Validate OTP and return error message if invalid
OTPValidator.validateOTP(otp: String?, context: Context): String?

// Check if OTP is valid (6 digits)
OTPValidator.isValidOTP(otp: String): Boolean
```

## Integration with RegisterScreen

The RegisterScreen has been updated to show the OTP verification bottom sheet after successful registration:

1. After user registers successfully, instead of navigating immediately to success screen, the OTP bottom sheet is shown
2. User enters the verification code
3. Once verified, navigation to success screen happens

## Styling

The components follow your app's design system:
- Uses `mainColor` from your theme
- Consistent with existing components like `MyButton` and `MyText`
- Follows material design principles

## Customization

You can customize:
- Number of OTP digits (default: 6)
- Colors (border, background, text)
- Error messages
- Button styles
- Bottom sheet behavior

## String Resources Added

Both English and Arabic strings have been added:

### English (`values/strings.xml`)
```xml
<string name="verify_email">Verify Email</string>
<string name="enter_verification_code_sent_to">Enter the verification code sent to</string>
<string name="verify_code">Verify Code</string>
<string name="resend_code">Resend Code</string>
<string name="resend_code_in">Resend code in</string>
<string name="otp_is_required">OTP is required</string>
<string name="invalid_otp">Invalid OTP code</string>
```

### Arabic (`values-ar/strings.xml`)
```xml
<string name="verify_email">تأكيد البريد الإلكتروني</string>
<string name="enter_verification_code_sent_to">أدخل رمز التحقق المرسل إلى</string>
<string name="verify_code">تأكيد الرمز</string>
<string name="resend_code">إعادة إرسال الرمز</string>
<string name="resend_code_in">إعادة إرسال الرمز خلال</string>
<string name="otp_is_required">رمز التحقق مطلوب</string>
<string name="invalid_otp">رمز التحقق غير صحيح</string>
```

## Testing

A preview file `OTPPreview.kt` has been created with:
- `OTPInputPreview` - Preview of the OTP input component
- `OTPBottomSheetPreview` - Preview of the bottom sheet

You can use these previews in Android Studio to see how the components look.

## Next Steps

1. **Backend Integration**: Connect the OTP verification to your actual backend API
2. **SMS Auto-fill**: Add SMS auto-fill functionality using SMS Retriever API
3. **Biometric**: Consider adding biometric authentication as an alternative
4. **Error Handling**: Implement proper error handling for network issues
5. **Analytics**: Add analytics to track OTP verification success/failure rates

The components are now ready to use and follow the same patterns as your existing Flutter code!
