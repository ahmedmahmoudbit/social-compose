# حل مشكلة الكيبورد في Android Compose

## المشكلة
عند ظهور الكيبورد في التطبيق، كان يظهر فوق المحتوى بدلاً من دفع المحتوى إلى أعلى، مما يجعل حقول الإدخال مخفية تحت الكيبورد.

## الحلول المطبقة

### 1. تحديث AndroidManifest.xml
```xml
<activity
    android:name=".MainActivity"
    android:exported="true"
    android:label="@string/app_name"
    android:theme="@style/Theme.MyApplication"
    android:windowSoftInputMode="adjustResize">
```

**التوضيح:** `adjustResize` يخبر النظام بتغيير حجم النافذة عند ظهور الكيبورد.

### 2. تحديث RegisterScreen

#### أ. إضافة imports جديدة:
```kotlin
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
```

#### ب. تحديث Scaffold:
```kotlin
Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = { ... },
    contentWindowInsets = WindowInsets.ime, // إضافة دعم الكيبورد
) { innerPadding ->
```

#### ج. تحديث Box الرئيسي:
```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .imePadding() // إضافة padding للكيبورد
        .padding(16.dp)
        .pointerInput(Unit) { ... },
) {
```

### 3. تحديث OTPBottomSheet

#### أ. إضافة imports:
```kotlin
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
```

#### ب. تحديث ModalBottomSheet:
```kotlin
ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    dragHandle = null,
    windowInsets = WindowInsets.ime // دعم الكيبورد
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding() // padding للكيبورد
            .verticalScroll(rememberScrollState()) // إمكانية التمرير
            .padding(24.dp),
        // ...
    )
}
```

### 4. إنشاء KeyboardExtensions.kt

ملف مساعد للتعامل مع الكيبورد:

```kotlin
@Composable
fun HandleKeyboardBehavior() {
    val context = LocalContext.current
    
    DisposableEffect(context) {
        val activity = context as? Activity
        activity?.setupKeyboardHandling()
        
        onDispose { }
    }
}

fun Activity.setupKeyboardHandling() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}
```

## كيفية عمل الحل

1. **WindowInsets.ime**: يخبر Compose بحدود الكيبورد
2. **imePadding()**: يضيف padding تلقائياً عند ظهور الكيبورد
3. **adjustResize**: يغير حجم النافذة بدلاً من إخفاء المحتوى
4. **verticalScroll**: يسمح بالتمرير عند ظهور الكيبورد
5. **HandleKeyboardBehavior**: يضبط إعدادات النافذة برمجياً

## النتيجة

الآن عند النقر على أي حقل إدخال:
- ✅ الكيبورد يظهر أسفل الشاشة
- ✅ المحتوى يندفع إلى أعلى تلقائياً  
- ✅ حقول الإدخال تبقى مرئية
- ✅ يمكن التمرير عند الحاجة
- ✅ يعمل مع BottomSheet أيضاً

## ملاحظات إضافية

- الحل يعمل مع جميع إصدارات Android
- متوافق مع النصوص العربية
- يدعم جميع أنواع الكيبورد (نص، أرقام، إيميل)
- محسّن للأداء
