package com.cleverpumpkin.cor.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.cor.presentation.theme.custom.LocalAppColorScheme
import com.cleverpumpkin.cor.presentation.theme.custom.LocalAppTypography

private val DarkColorScheme = com.cleverpumpkin.cor.presentation.theme.custom.AppColorScheme(
    supportSeparator = DarkSeparator,
    supportOverlay = DarkOverlay,
    labelPrimary = DarkPrimary,
    labelSecondary = DarkSecondary,
    labelTertiary = DarkTertiary,
    labelDisable = DarkDisabled,
    red = DarkRed,
    green = DarkGreen,
    blue = DarkBlue,
    lightBlue = DarkLightblue,
    gray = DarkGray,
    lightGray = DarkGrayLight,
    backElevated = DarkBackElevated,
    backPrimary = DarkBackPrimary,
    backSecondary = DarkBackSecondary,
    white = White
)

private val LightColorScheme = com.cleverpumpkin.cor.presentation.theme.custom.AppColorScheme(
    supportSeparator = LightSeparator,
    supportOverlay = LightOverlay,
    labelPrimary = LightPrimary,
    labelSecondary = LightSecondary,
    labelTertiary = LightTertiary,
    labelDisable = LightDisabled,
    red = LightRed,
    green = LightGreen,
    blue = LightBlue,
    lightBlue = LightLightblue,
    gray = LightGray,
    lightGray = LightGrayLight,
    backElevated = LightBackElevated,
    backPrimary = LightBackPrimary,
    backSecondary = LightBackSecondary,
    white = White
)

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides Typography,
        content = content
    )
}

object TodoAppTheme {
    val colorScheme: com.cleverpumpkin.cor.presentation.theme.custom.AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: com.cleverpumpkin.cor.presentation.theme.custom.AppTypography
        @Composable get() = LocalAppTypography.current
}

@PreviewLightDark
@Composable
fun TodoAppThemePreview() {
    TodoAppTheme {
        ColorPalette()
    }
}

@Composable
fun ColorItem(colorName: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color = color)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = colorName, style = TodoAppTheme.typography.body)
    }
}

@Composable
fun ColorPalette() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Color Palette", style = TodoAppTheme.typography.body)

        ColorItem(colorName = "Support Separator", color = TodoAppTheme.colorScheme.supportSeparator)
        ColorItem(colorName = "Support Overlay", color = TodoAppTheme.colorScheme.supportOverlay)
        ColorItem(colorName = "Label Primary", color = TodoAppTheme.colorScheme.labelPrimary)
        ColorItem(colorName = "Label Secondary", color = TodoAppTheme.colorScheme.labelSecondary)
        ColorItem(colorName = "Label Tertiary", color = TodoAppTheme.colorScheme.labelTertiary)
        ColorItem(colorName = "Label Disable", color = TodoAppTheme.colorScheme.labelDisable)
        ColorItem(colorName = "Red", color = TodoAppTheme.colorScheme.red)
        ColorItem(colorName = "Green", color = TodoAppTheme.colorScheme.green)
        ColorItem(colorName = "Blue", color = TodoAppTheme.colorScheme.blue)
        ColorItem(colorName = "Light Blue", color = TodoAppTheme.colorScheme.lightBlue)
        ColorItem(colorName = "Gray", color = TodoAppTheme.colorScheme.gray)
        ColorItem(colorName = "Light Gray", color = TodoAppTheme.colorScheme.lightGray)
        ColorItem(colorName = "Back Elevated", color = TodoAppTheme.colorScheme.backElevated)
        ColorItem(colorName = "Back Primary", color = TodoAppTheme.colorScheme.backPrimary)
        ColorItem(colorName = "Back Secondary", color = TodoAppTheme.colorScheme.backSecondary)
        ColorItem(colorName = "White", color = TodoAppTheme.colorScheme.white)
    }
}
