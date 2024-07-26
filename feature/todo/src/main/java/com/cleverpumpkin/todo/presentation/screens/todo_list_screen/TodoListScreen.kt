package com.cleverpumpkin.todo.presentation.screens.todo_list_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R
import com.cleverpumpkin.todo.domain.todo_model.TodoItem
import com.cleverpumpkin.todo.presentation.composable_elements.RefreshBlock
import com.cleverpumpkin.todo.presentation.screens.todo_list_screen.composables.CollapsingTopAppBar
import com.cleverpumpkin.todo.presentation.screens.todo_list_screen.composables.TodoList
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    state: State<TodoListUiState>,
    onEndToStartAction: (TodoItem) -> Unit,
    onAddItem: () -> Unit,
    onNavigate: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onFilter: () -> Unit,
    onCheck: (TodoItem) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
    val uiState = state.value
    val formatter = remember { DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM) }
    Scaffold(
        modifier = modifier.background(TodoAppTheme.colorScheme.backPrimary),
        topBar = {
            AnimatedVisibility(visible = uiState.errorCode == null) {
                CollapsingTopAppBar(
                    scrollBehavior = scrollBehavior,
                    modifier = Modifier.fillMaxWidth(),
                    onIconClick = { onFilter() },
                    onSettingsIconClick = { onNavigateToSettings() },
                    isFiltered = uiState.isFiltered,
                    completed = uiState.completed
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(visible = uiState.errorCode == null) {
                FloatingActionButton(
                    onClick = { onAddItem() },
                    containerColor = TodoAppTheme.colorScheme.blue,
                    contentColor = TodoAppTheme.colorScheme.white,
                    elevation = FloatingActionButtonDefaults.elevation(6.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = stringResource(id = R.string.add_todo_task)
                    )
                }
            }
        }
    ) { paddingValues ->
        when (uiState.errorCode) {
            null -> {
                TodoList(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TodoAppTheme.colorScheme.backPrimary)
                        .padding(paddingValues),
                    scrollBehavior = scrollBehavior,
                    items = uiState.items,
                    onDelete = { item -> onEndToStartAction(item) },
                    onNavigate = { id -> onNavigate(id) },
                    onCheck = { item -> onCheck(item) },
                    onAddItem = { onAddItem() },
                    formatter = formatter
                )
            }

            else -> {
                RefreshBlock(
                    errorCode = uiState.errorCode,
                    onRefresh = { onRefresh() },
                    modifier = Modifier.fillMaxSize()
                        .background(TodoAppTheme.colorScheme.backPrimary)
                        .padding(paddingValues)
                )
            }
        }
    }
}