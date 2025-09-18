package com.example.myapplication.utils.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MyText(
    title: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = style.color,
    size: TextUnit = style.fontSize,
    align: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE,
    fontFamily: FontFamily? = style.fontFamily,
    fontWeight: FontWeight? = style.fontWeight,
    heightTxt: Float? = null,
    isFittedBox: Boolean = false,
    isTablet: Boolean = false,
    showCurrency: Boolean = true
) {
    val isNumeric = remember(title) { title.matches(Regex("^[\\d,.]+$")) }

    val textWidget = Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
//        if (isNumeric && showCurrency) {
//            MySvg(
//                img = "assets/svgs/riyal.svg",
//                height = size.value,
//                color = color
//            )
//            Spacer(modifier = Modifier.width(3.dp))
//        }

        Text(
            text = title,
            textAlign = align,
            overflow = if (maxLines == 1) TextOverflow.Ellipsis else TextOverflow.Clip,
            maxLines = maxLines,
            style = style.copy(
                color = color,
                fontFamily = fontFamily,
                fontSize = size,
                fontWeight = fontWeight,
                letterSpacing = 0.6.sp,
                lineHeight = heightTxt?.sp ?: if (isTablet) 0.sp else 1.4.sp,
                textDirection = TextDirection.Ltr
            )
        )
    }

    if (isFittedBox) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
        ) {
            textWidget
        }
    } else {
        textWidget
    }
}
