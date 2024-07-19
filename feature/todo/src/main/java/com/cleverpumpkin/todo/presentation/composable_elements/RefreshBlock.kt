package com.cleverpumpkin.todo.presentation.composable_elements

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
import com.cleverpumpkin.cor.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.presentation.utils.getErrorStringResource

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
