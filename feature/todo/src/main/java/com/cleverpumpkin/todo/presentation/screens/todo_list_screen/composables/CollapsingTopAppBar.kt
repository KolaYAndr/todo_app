package com.cleverpumpkin.todo.presentation.screens.todo_list_screen.composables

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
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R

private const val COLLAPSED_STATE = 0.5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFiltered: Boolean,
    completed: Int
) {
    LargeTopAppBar(
        modifier = modifier.semantics { isTraversalGroup = true },
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
                if (scrollBehavior.state.collapsedFraction < COLLAPSED_STATE) {
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
                        Row {
                            IconButton(
                                modifier = Modifier.semantics { traversalIndex = 1f },
                                onClick = { onIconClick() },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = TodoAppTheme.colorScheme.blue)
                            ) {
                                Icon(
                                    painter = painterResource(id = if (isFiltered) R.drawable.eye else R.drawable.striked_eye),
                                    contentDescription = stringResource(id = R.string.show_done_tasks)
                                )
                            }

                            IconButton(
                                modifier = Modifier.semantics { traversalIndex = 2f },
                                onClick = { onSettingsIconClick() },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = TodoAppTheme.colorScheme.blue)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.settings),
                                    contentDescription = stringResource(id = R.string.settings)
                                )
                            }
                        }
                    }
                }
            }
        },
        actions = {
            AnimatedVisibility(visible = scrollBehavior.state.collapsedFraction >= COLLAPSED_STATE) {
                Row {
                    IconButton(
                        modifier = Modifier.semantics { traversalIndex = 1f },
                        onClick = { onIconClick() }) {
                        Icon(
                            painter = painterResource(id = if (isFiltered) R.drawable.eye else R.drawable.striked_eye),
                            contentDescription = stringResource(id = R.string.show_done_tasks)
                        )
                    }

                    IconButton(
                        modifier = Modifier.semantics { traversalIndex = 2f },
                        onClick = { onSettingsIconClick() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.settings),
                            contentDescription = stringResource(id = R.string.settings)
                        )
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun PreviewTopAppBar() {
    TodoAppTheme {
        val scroll = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        CollapsingTopAppBar(
            scrollBehavior = scroll,
            modifier = Modifier.fillMaxWidth(),
            onIconClick = {},
            isFiltered = false,
            completed = 5,
            onSettingsIconClick = {}
        )
    }
}
