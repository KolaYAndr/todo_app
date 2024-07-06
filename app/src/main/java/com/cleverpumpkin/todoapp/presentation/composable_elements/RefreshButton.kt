package com.cleverpumpkin.todoapp.presentation.composable_elements

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme

@Composable
fun RefreshButton(onRefresh: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(modifier = modifier, onClick = { onRefresh() }) {
        Text(
            text = stringResource(R.string.refresh),
            style = TodoAppTheme.typography.button,
            color = TodoAppTheme.colorScheme.blue
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewRefreshButton() {
    RefreshButton(onRefresh = { })
}
