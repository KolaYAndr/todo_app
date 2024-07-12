package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.cor.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R

@Composable
fun ImportanceBlock(
    importanceText: String,
    modifier: Modifier = Modifier
) {
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
            color = TodoAppTheme.colorScheme.labelTertiary
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