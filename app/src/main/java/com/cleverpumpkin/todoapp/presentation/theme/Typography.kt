package com.cleverpumpkin.todoapp.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cleverpumpkin.todoapp.presentation.theme.custom.AppTypography

val Typography = AppTypography(
    largeTitle = TextStyle(
        fontFamily = RobotoFamily,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.Normal
    ),
    title = TextStyle(
        fontFamily = RobotoFamily,
        fontSize = 20.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Normal
    ),
    button = TextStyle(
        fontFamily = RobotoFamily,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.16.sp,
        fontWeight = FontWeight.Normal
    ),
    body = TextStyle(
        fontFamily = RobotoFamily,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    subhead = TextStyle(
        fontFamily = RobotoFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal
    )
)