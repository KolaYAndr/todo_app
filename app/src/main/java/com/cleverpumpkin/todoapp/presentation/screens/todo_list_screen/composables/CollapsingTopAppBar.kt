package com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFiltered: Boolean,
    completed: Int
) {
    LargeTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TodoAppTheme.colorScheme.backPrimary,
            scrolledContainerColor = TodoAppTheme.colorScheme.backPrimary,
            actionIconContentColor = TodoAppTheme.colorScheme.blue
        ),
        title = {
            Column {
                Text(
                    text = stringResource(id = R.string.my_tasks),
                    fontWeight = FontWeight.Bold,
                    style = TodoAppTheme.typography.largeTitle,
                    color = TodoAppTheme.colorScheme.labelPrimary
                )
                if (scrollBehavior.state.collapsedFraction < 0.5) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.done, completed),
                            style = TodoAppTheme.typography.body,
                            color = TodoAppTheme.colorScheme.labelTertiary,
                        )
                        IconButton(
                            onClick = { onIconClick() },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = TodoAppTheme.colorScheme.blue)
                        ) {
                            Icon(
                                painter = painterResource(id = if (isFiltered) R.drawable.eye else R.drawable.striked_eye),
                                contentDescription = stringResource(id = R.string.show_done_tasks)
                            )
                        }
                    }
                }
            }
        },
        actions = {
            AnimatedVisibility(visible = scrollBehavior.state.collapsedFraction >= 0.5) {
                IconButton(onClick = { onIconClick() }) {
                    Icon(
                        painter = painterResource(id = if (isFiltered) R.drawable.eye else R.drawable.striked_eye),
                        contentDescription = stringResource(id = R.string.show_done_tasks)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewTopAppBar() {
    val scroll = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    CollapsingTopAppBar(
        scrollBehavior = scroll,
        modifier = Modifier.fillMaxWidth(),
        onIconClick = {},
        isFiltered = false,
        completed = 5
    )
}