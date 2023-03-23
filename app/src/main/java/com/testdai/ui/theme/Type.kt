package com.testdai.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.testdai.R

private object Fonts {
    val regular = FontFamily(Font(R.font.montserrat_regular))
    val medium = FontFamily(Font(R.font.montserrat_medium))
    val bold = FontFamily(Font(R.font.montserrat_semibold))
}

// style = MaterialTheme.typography.subtitle1,
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = Fonts.bold,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    h2 = TextStyle(
        fontFamily = Fonts.medium,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.25.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = Fonts.medium,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.sp
    ),
    body1 = TextStyle(
        fontFamily = Fonts.medium,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.sp
    ),
    body2 = TextStyle(
        fontFamily = Fonts.regular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.sp
    ),
    overline = TextStyle(
        fontFamily = Fonts.medium,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.sp
    )
)