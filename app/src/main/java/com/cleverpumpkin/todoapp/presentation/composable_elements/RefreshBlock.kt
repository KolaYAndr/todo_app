package com.cleverpumpkin.todoapp.presentation.composable_elements

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
import com.cleverpumpkin.todoapp.R
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
                id = when (errorCode) {
                    404 -> R.string.network_error_404
                    401 -> R.string.network_error_401
                    403 -> R.string.network_error_403
                    408 -> R.string.network_error_408
                    500 -> R.string.network_error_500
                    else -> R.string.unexpected_error
                }
            ),
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        RefreshButton(onRefresh = { onRefresh() })
    }
}
