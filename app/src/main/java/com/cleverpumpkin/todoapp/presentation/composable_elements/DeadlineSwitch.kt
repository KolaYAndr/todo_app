package com.cleverpumpkin.todoapp.presentation.composable_elements

import androidx.compose.foundation.background
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme

@Composable
fun DeadlineSwitch(
    isDeadlineSet: Boolean,
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        modifier = modifier,
        checked = isDeadlineSet,
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
        DeadlineSwitch(
            isDeadlineSet = isDeadline,
            onSwitch = {},
            modifier = Modifier.background(TodoAppTheme.colorScheme.backPrimary)
        )
    }
}
