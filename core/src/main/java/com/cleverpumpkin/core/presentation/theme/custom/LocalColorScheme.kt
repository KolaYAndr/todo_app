package com.cleverpumpkin.core.presentation.theme.custom

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColorScheme(
    val supportSeparator: Color,
    val supportOverlay: Color,
    val labelPrimary: Color,
    val labelSecondary: Color,
    val labelTertiary: Color,
    val labelDisable: Color,
    val red: Color,
    val green: Color,
    val blue: Color,
    val lightBlue: Color,
    val gray: Color,
    val lightGray: Color,
    val white: Color,
    val backPrimary: Color,
    val backSecondary: Color,
    val backElevated: Color,
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        supportSeparator = Color.Unspecified,
        supportOverlay = Color.Unspecified,
        labelPrimary = Color.Unspecified,
        labelSecondary = Color.Unspecified,
        labelTertiary = Color.Unspecified,
        labelDisable = Color.Unspecified,
        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backElevated = Color.Unspecified,
        red = Color.Unspecified,
        green = Color.Unspecified,
        blue = Color.Unspecified,
        lightBlue = Color.Unspecified,
        gray = Color.Unspecified,
        lightGray = Color.Unspecified,
        white = Color.Unspecified
    )
}
