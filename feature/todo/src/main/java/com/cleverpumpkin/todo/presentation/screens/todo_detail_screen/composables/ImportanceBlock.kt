package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.cor.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ImportanceBlock(
    importanceText: String,
    modifier: Modifier = Modifier
) {
    var showColor by remember { mutableStateOf(false) }

    val textColor by animateColorAsState(
        targetValue = if (showColor) TodoAppTheme.colorScheme.red else TodoAppTheme.colorScheme.labelTertiary,
        animationSpec = tween(durationMillis = 1000)
    )

    val scope = rememberCoroutineScope()

    val flag = importanceText == stringResource(R.string.importance_urgent)

    LaunchedEffect(importanceText) {
        if (flag) {
            scope.launch {
                showColor = true
                delay(1000) // Delay for 0.5 seconds
                showColor = false
            }
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.importance),
            style = TodoAppTheme.typography.body,
            color = TodoAppTheme.colorScheme.labelPrimary
        )
        Text(
            text = importanceText,
            style = TodoAppTheme.typography.subhead,
            color = textColor
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewImportance() {
    TodoAppTheme {
        ImportanceBlock(
            importanceText = stringResource(id = R.string.importance_low),
            modifier = Modifier
                .fillMaxWidth()
                .background(TodoAppTheme.colorScheme.backPrimary)
                .padding(16.dp)
        )
    }
}
