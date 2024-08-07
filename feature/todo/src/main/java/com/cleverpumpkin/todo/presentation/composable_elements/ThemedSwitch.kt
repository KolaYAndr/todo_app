package com.cleverpumpkin.todo.presentation.composable_elements

import androidx.compose.foundation.background
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme

@Composable
fun ThemedSwitch(
    checked: Boolean,
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = { onSwitch(it) },
        colors = SwitchDefaults.colors(
            checkedThumbColor = TodoAppTheme.colorScheme.blue,
            uncheckedThumbColor = TodoAppTheme.colorScheme.backElevated,
            checkedTrackColor = TodoAppTheme.colorScheme.lightBlue,
            uncheckedBorderColor = Color.Transparent,
            uncheckedTrackColor = TodoAppTheme.colorScheme.supportOverlay
        )
    )
}

@PreviewLightDark
@Composable
fun PreviewSwitch() {
    TodoAppTheme {
        val isDeadline = false
        ThemedSwitch(
            checked = isDeadline,
            onSwitch = {},
            modifier = Modifier.background(TodoAppTheme.colorScheme.backPrimary)
        )
    }
}
