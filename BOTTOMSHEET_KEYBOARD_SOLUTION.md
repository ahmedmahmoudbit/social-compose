# إعداد الكيبورد للـ OTP BottomSheet فقط

## المشكلة السابقة
كان الكيبورد يظهر أسفل صفحة التسجيل بأكملها، مما يؤثر على تجربة المستخدم في صفحة التسجيل.

## الحل الجديد
الآن الكيبورد يظهر أسفل الـ OTP BottomSheet فقط، مما يوفر تجربة أفضل للمستخدم.

## التغييرات المطبقة

### 1. إعادة تعيين RegisterScreen
```kotlin
// تمت إزالة جميع إعدادات الكيبورد من RegisterScreen
// إزالة WindowInsets.ime
// إزالة imePadding()
// إزالة HandleKeyboardBehavior()
```

### 2. تحسين OTPBottomSheet

#### أ. إضافة imports محددة:
```kotlin
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.layout.navigationBarsPadding
```

#### ب. تحديث ModalBottomSheet:
```kotlin
ModalBottomSheet(
    onDismissRequest = { 
        keyboardController?.hide() // إخفاء الكيبورد عند الإغلاق
        onDismiss()
    },
    sheetState = sheetState,
    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    dragHandle = null,
    windowInsets = WindowInsets(0) // تجاهل النوافذ الافتراضية
)
```

#### ج. تحسين Column:
```kotlin
Column(
    modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding() // padding للتنقل السفلي
        .imePadding() // padding للكيبورد فقط هنا
        .verticalScroll(rememberScrollState()) // إمكانية التمرير
        .padding(horizontal = 24.dp)
        .padding(top = 24.dp, bottom = 16.dp),
    // ...
)
```

### 3. تحديث AndroidManifest.xml
```xml
android:windowSoftInputMode="adjustPan"
```
`adjustPan` يحرك النافذة بدلاً من تغيير حجمها، مما يجعل الكيبورد يظهر فقط عند الحاجة.

### 4. إزالة KeyboardExtensions.kt
تمت إزالة استخدام HandleKeyboardBehavior() من RegisterScreen.

## كيفية عمل النظام الجديد

### في صفحة التسجيل:
- ✅ الكيبورد **لا يؤثر** على تخطيط الصفحة
- ✅ الحقول تعمل بشكل طبيعي دون تغيير التخطيط
- ✅ تجربة مستخدم سلسة ومستقرة

### في OTP BottomSheet:
- ✅ الكيبورد يظهر **أسفل BottomSheet فقط**
- ✅ المحتوى يتحرك تلقائياً عند ظهور الكيبورد
- ✅ يمكن التمرير عند الحاجة
- ✅ الكيبورد يختفي عند إغلاق BottomSheet

## المميزات الجديدة

1. **تجربة مستخدم محسّنة**: الكيبورد يظهر فقط حيث يحتاجه المستخدم
2. **أداء أفضل**: عدم تأثير الكيبورد على الصفحة الرئيسية
3. **تصميم متسق**: BottomSheet يتعامل مع الكيبورد بشكل مستقل
4. **إدارة ذكية للتركيز**: الكيبورد يختفي تلقائياً عند إغلاق BottomSheet

## اختبار النظام

### صفحة التسجيل:
1. انقر على أي حقل إدخال
2. الكيبورد يظهر ولكن **لا يغير تخطيط الصفحة**
3. يمكنك التمرير بحرية

### OTP BottomSheet:
1. بعد التسجيل الناجح، يظهر BottomSheet
2. انقر على مربعات OTP
3. الكيبورد يظهر **أسفل BottomSheet فقط**
4. المحتوى يتحرك تلقائياً
5. عند الإغلاق، الكيبورد يختفي تلقائياً

هذا التصميم يوفر تجربة مستخدم مثلى حيث كل مكون يدير الكيبورد حسب احتياجاته!
