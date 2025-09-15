# حل مشكلة الكيبورد في OTP BottomSheet

## المشكلة
الكيبورد كان يظهر فوق المحتوى في OTP BottomSheet، مما يخفي حقول إدخال OTP.

## الحل المطبق

### 1. تحديث OTPBottomSheet

#### أ. تغيير WindowInsets:
```kotlin
// قبل - كان يتجاهل الكيبورد
windowInsets = WindowInsets(0)

// بعد - يستجيب للكيبورد
windowInsets = WindowInsets.ime
```

#### ب. تحسين Modifiers:
```kotlin
Column(
    modifier = Modifier
        .fillMaxWidth()
        .imePadding() // يضيف padding عند ظهور الكيبورد
        .verticalScroll(rememberScrollState()) // يسمح بالتمرير
        .padding(horizontal = 24.dp)
        .padding(top = 24.dp, bottom = 24.dp), // زيادة bottom padding
    // ...
)
```

#### ج. إعادة تعيين skipPartiallyExpanded:
```kotlin
// يمنع التوسيط الجزئي للتحكم الأفضل في الارتفاع
val sheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true
)
```

### 2. تحديث AndroidManifest.xml

```xml
<!-- تغيير من adjustPan إلى adjustResize -->
android:windowSoftInputMode="adjustResize"
```

**الفرق:**
- `adjustPan`: يحرك النافذة عند ظهور الكيبورد
- `adjustResize`: يغير حجم النافذة عند ظهور الكيبورد (الأفضل للـ BottomSheet)

### 3. تفعيل OTP BottomSheet في RegisterScreen

```kotlin
is LoginState.Success -> {
    // تم تعطيل الانتقال المباشر
    // navController.navigate(RouteSuccessScreen(...))
    
    // تفعيل OTP BottomSheet
    showOTPBottomSheet = true
}
```

## كيفية عمل الحل

### عند ظهور الكيبورد:
1. **WindowInsets.ime** يخبر النظام أن BottomSheet يجب أن يستجيب للكيبورد
2. **imePadding()** يضيف padding تلقائياً بحجم الكيبورد
3. **adjustResize** يغير حجم النافذة لتتناسب مع الكيبورد
4. **verticalScroll** يسمح بالتمرير في المحتوى إذا لزم الأمر

### النتيجة النهائية:
- ✅ المحتوى يظهر **فوق الكيبورد**
- ✅ حقول OTP مرئية دائماً
- ✅ يمكن التمرير عند الحاجة
- ✅ تجربة مستخدم محسنة

## اختبار الحل

1. **افتح التطبيق** وانتقل لصفحة التسجيل
2. **املأ البيانات** واضغط تسجيل
3. **انتظر ظهور OTP BottomSheet**
4. **انقر على حقول OTP** - يجب أن يظهر الكيبورد أسفل المحتوى
5. **تأكد أن جميع حقول OTP مرئية** فوق الكيبورد

## تحسينات إضافية مطبقة

### إدارة محسنة للكيبورد:
```kotlin
val keyboardController = LocalSoftwareKeyboardController.current

onDismissRequest = { 
    keyboardController?.hide() // إخفاء الكيبورد عند الإغلاق
    onDismiss()
}
```

### padding محسن:
```kotlin
.padding(top = 24.dp, bottom = 24.dp) // مساحة كافية في الأعلى والأسفل
```

### تمرير مرن:
```kotlin
.verticalScroll(rememberScrollState()) // تمرير عند الحاجة
```

هذا الحل يضمن تجربة مستخدم ممتازة حيث يكون المحتوى دائماً مرئياً فوق الكيبورد!
