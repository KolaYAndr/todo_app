package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R
import com.cleverpumpkin.todo.presentation.composable_elements.ThemedSwitch

@Composable
fun DeadlineBlock(
    isDeadlineSet: Boolean,
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    deadlineText: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top)) {
            Text(
                text = stringResource(id = R.string.deadline),
                style = TodoAppTheme.typography.body,
                color = TodoAppTheme.colorScheme.labelPrimary
            )
            AnimatedVisibility(isDeadlineSet) {
                Text(
                    text = deadlineText,
                    style = TodoAppTheme.typography.subhead,
                    color = TodoAppTheme.colorScheme.blue
                )
            }
        }
        ThemedSwitch(
            checked = isDeadlineSet,
            onSwitch = { onSwitch(it) }
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewDeadline() {
    TodoAppTheme {
        val deadline = true
        DeadlineBlock(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            isDeadlineSet = deadline,
            deadlineText = "2 июня 2021",
            onSwitch = {}
        )
    }
}