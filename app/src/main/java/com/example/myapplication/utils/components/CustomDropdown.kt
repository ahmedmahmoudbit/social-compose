package com.example.myapplication.utils.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomDropdown(
    name: String,
    items: List<T>,
    itemLabelBuilder: @Composable (T, Int) -> String,
    initialValue: T? = null,
    onChanged: ((T?) -> Unit)? = null,
    labelText: String? = null,
    dropdownColor: Color = Color.White,
    textColor: Color = Color.Black,
    borderColor: Color = Color.Gray
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(initialValue) }

    Column {
        Text(text = labelText ?: name, fontSize = 14.sp, color = textColor)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedItem?.let { itemLabelBuilder(it, items.indexOf(it)) } ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    focusedBorderColor = borderColor,
                    cursorColor = textColor
                ),
                shape = RoundedCornerShape(8.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = itemLabelBuilder(item, index)) },
                        onClick = {
                            selectedItem = item
                            onChanged?.invoke(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// How to use
//val items = listOf("العنصر 1", "العنصر 2", "العنصر 3")
//var selected by remember { mutableStateOf<String?>(null) }
//
//CustomDropdown(
//        name = "القائمة المنسدلة",
//        items = items,
//        itemLabelBuilder = { item, _ -> item },
//initialValue = selected,
//onChanged = { selected = it },
//labelText = "اختر عنصرًا",
//dropdownColor = Color.White,
//textColor = Color.Black,
//borderColor = Color.Gray
//)

