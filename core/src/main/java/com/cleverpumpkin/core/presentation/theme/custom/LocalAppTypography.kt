package com.cleverpumpkin.core.presentation.theme.custom

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class AppTypography(
    val largeTitle: TextStyle,
    val title: TextStyle,
    val button: TextStyle,
    val body: TextStyle,
    val subhead: TextStyle
)

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        largeTitle = TextStyle.Default,
        title = TextStyle.Default,
        button = TextStyle.Default,
        body = TextStyle.Default,
        subhead = TextStyle.Default,
    )
}
