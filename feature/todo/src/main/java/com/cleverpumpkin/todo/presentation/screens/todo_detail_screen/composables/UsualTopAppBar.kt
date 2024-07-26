package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsualTopAppBar(
    onSave: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TodoAppTheme.colorScheme.backPrimary
        ),
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { onNavigate() },
                colors = IconButtonDefaults.iconButtonColors(contentColor = TodoAppTheme.colorScheme.labelPrimary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = stringResource(
                        R.string.close
                    )
                )
            }
        },
        actions = {
            TextButton(
                onClick = { onSave() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = TodoAppTheme.colorScheme.blue
                )
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    style = TodoAppTheme.typography.button
                )
            }
        }
    )
}

@PreviewLightDark
@Composable
fun PreviewTopBar() {
    TodoAppTheme {
        UsualTopAppBar(
            onSave = {},
            onNavigate = {}
        )
    }
}