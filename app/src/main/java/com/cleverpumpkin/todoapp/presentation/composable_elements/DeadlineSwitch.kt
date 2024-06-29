package com.cleverpumpkin.todoapp.presentation.composable_elements

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme

@Composable
fun DeadlineSwitch(
    isDeadlineSet: MutableState<Boolean>,
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        modifier = modifier,
        checked = isDeadlineSet.value,
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

@Preview
@Composable
fun PreviewSwitch() {
    val isDeadline = remember { mutableStateOf(false) }
    DeadlineSwitch(isDeadlineSet = isDeadline, onSwitch = {})
}