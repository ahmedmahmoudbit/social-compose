package com.example.myapplication.utils.extensions

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Extension functions and composables for handling keyboard behavior
 */

/**
 * Sets up the activity to handle keyboard properly with Compose
 */
fun Activity.setupKeyboardHandling() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}

/**
 * Composable that sets up proper keyboard handling for the screen
 */
@Composable
fun HandleKeyboardBehavior() {
    val context = LocalContext.current
    
    DisposableEffect(context) {
        val activity = context as? Activity
        activity?.setupKeyboardHandling()
        
        onDispose {
            // Clean up if needed
        }
    }
}

/**
 * Hide keyboard programmatically
 */
fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
    var view = currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
