package com.cleverpumpkin.todoapp.presentation.screens.todo_detail_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.presentation.composable_elements.DeadlineSwitch
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme

@Composable
fun DeadlineBlock(
    isDeadlineSet: MutableState<Boolean>,
    onSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    deadlineText: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column{
            Text(
                text = stringResource(id = R.string.deadline),
                style = TodoAppTheme.typography.body,
                color = TodoAppTheme.colorScheme.labelPrimary
            )
            if (isDeadlineSet.value) {
                Text(
                    text = deadlineText,
                    style = TodoAppTheme.typography.subhead,
                    color = TodoAppTheme.colorScheme.blue
                )
            }
        }
        DeadlineSwitch(
            isDeadlineSet = isDeadlineSet,
            onSwitch = { onSwitch(it) }
        )
    }
}

@Preview
@Composable
fun PreviewDeadline() {
    val deadline = remember { mutableStateOf(true) }
    DeadlineBlock(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        isDeadlineSet = deadline,
        deadlineText = "2 июня 2021",
        onSwitch = {}
    )
}