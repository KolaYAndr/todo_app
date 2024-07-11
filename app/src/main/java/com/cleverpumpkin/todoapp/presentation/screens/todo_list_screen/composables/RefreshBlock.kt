package com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.todoapp.presentation.utils.getErrorStringResource
import com.cleverpumpkin.todoapp.presentation.composable_elements.RefreshButton
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme

@Composable
fun RefreshBlock(errorCode: Int, onRefresh: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(TodoAppTheme.colorScheme.backPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                id = getErrorStringResource(errorCode)
            ),
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        RefreshButton(onRefresh = { onRefresh() })
    }
}
