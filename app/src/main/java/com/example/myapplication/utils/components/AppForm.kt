package com.example.myapplication.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.mainColor


@Composable
fun AppForm(
    modifier: Modifier = Modifier,
    prefixIcon: String? = null,
    prefixWidget: (@Composable (() -> Unit))? = null,
    suffixIcon: (@Composable (() -> Unit))? = null,
    controller: MutableState<String> = remember { mutableStateOf("") },
    type: KeyboardType = KeyboardType.Text,
    error: String? = null,
    backgroundColor: Color = mainColor.copy(alpha = 0.2f),
    cornerRadius: Dp = 8.dp,
    onValidateError: Boolean = true,
    validator: ((String) -> String?)? = null,
    isPassword: Boolean = false,
    textInputAction: ImeAction = ImeAction.Next,
    onSubmit: ((String) -> Unit)? = null,
    onChanged: ((String) -> Unit)? = null,
    onSaved: ((String) -> Unit)? = null,
    maxLines: Int = 1,
    enabled: Boolean = true,
    hintText: String? = null,
    onTap: (() -> Unit)? = null,
    focusRequester: FocusRequester = remember { FocusRequester() },
    autofocus: Boolean = false,
    inputFormats: List<(String) -> String> = emptyList()
) {
    var text by controller
    var isVisible by remember { mutableStateOf(isPassword) }
    var isTouched by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier
        .fillMaxWidth()
        .background(backgroundColor, shape = RoundedCornerShape(cornerRadius))
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = inputFormats.fold(it) { acc, formatter -> formatter(acc) }
                onChanged?.invoke(it)
                isTouched = true
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            singleLine = maxLines == 1,
            maxLines = maxLines,
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = backgroundColor,
                focusedBorderColor = mainColor,
                cursorColor = mainColor
            ),
            visualTransformation = if (!isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = type,
                imeAction = textInputAction
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    if (textInputAction == ImeAction.Next) {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                },
                onDone = {
                    onSubmit?.invoke(text)
                    focusManager.clearFocus()
                }
            ),
            isError = onValidateError && isTouched && validator?.invoke(text) != null,
            placeholder = hintText?.let { { Text(it, color = Color.Gray) } },
            leadingIcon = prefixWidget ?: prefixIcon?.let {
                {
//                    MySvg(img = it, fill = false, height = 20.dp)
                }
            },
            trailingIcon = suffixIcon ?: if (isPassword) {
                {
                    IconButton(
                        onClick = { isVisible = !isVisible }) {
                        Icon(
                            imageVector = if (isVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                            tint = mainColor,
                            contentDescription = null
                        )
                    }
                }
            } else null,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused && autofocus) focusRequester.requestFocus()
                }
                .clickable { onTap?.invoke() }
        )

        if (onValidateError && isTouched && validator?.invoke(text) != null) {
            Text(
                text = validator.invoke(text) ?: "",
                color = Color.Red,
                fontSize = 10.sp,
                modifier = Modifier.padding(
                    start = 16.dp,
                    bottom = 5.dp,
                    top = 5.dp,
                    end = 16.dp
                )
            )
        }
    }
}