package com.cleverpumpkin.todoapp.presentation.screens.todo_list_screen.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.presentation.composable_elements.NewButton
import com.cleverpumpkin.todoapp.presentation.composable_elements.SwipeableBackground
import com.cleverpumpkin.todoapp.presentation.composable_elements.TodoItemView
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(
    scrollBehavior: TopAppBarScrollBehavior,
    items: List<TodoItem>,
    onDelete: (TodoItem) -> Unit,
    onNavigate: (String) -> Unit,
    onCheck: (TodoItem) -> Unit,
    onAddItem: () -> Unit,
    formatter: DateTimeFormatter,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(TodoAppTheme.colorScheme.backPrimary)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items) { todo ->
            val expandDropMenu = remember { mutableStateOf(false) }
            SwipeableBackground(
                onEndToStart = { onDelete(todo) },
                onStartToEnd = {
                    onCheck(todo)
                },
                onClick = { onNavigate(todo.id) },
                onLongClick = { expandDropMenu.value = true },
                modifier = Modifier.fillParentMaxWidth()
            ) {
                TodoItemView(
                    item = todo,
                    formatter = formatter,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .background(TodoAppTheme.colorScheme.backPrimary)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    onCheckedChange = {
                        onCheck(todo)
                    }
                )
                AnimatedVisibility(visible = expandDropMenu.value) {
                    DropdownMenu(
                        modifier = Modifier
                            .background(TodoAppTheme.colorScheme.backPrimary)
                            .wrapContentSize(),
                        expanded = expandDropMenu.value,
                        onDismissRequest = { expandDropMenu.value = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.delete)) },
                            onClick = {
                                onDelete(todo)
                                expandDropMenu.value = false
                            }
                        )
                        if (!todo.isDone) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.mark_done)) },
                                onClick = {
                                    onCheck(todo)
                                    expandDropMenu.value = false
                                }
                            )
                        }
                    }
                }
            }
        }
        item {
            NewButton(onClick = { onAddItem() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
fun PreviewTodoList() {
    TodoAppTheme {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val topAppBarState = rememberTopAppBarState()
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
        TodoList(
            scrollBehavior = scrollBehavior, items = mutableListOf(
                TodoItem(
                    id = "1",
                    text = "Купить продукты",
                    importance = Importance.Normal,
                    deadline = LocalDateTime.of(2024, 6, 20, 12, 0),
                    isDone = false,
                    createdAt = LocalDateTime.now()
                ),
                TodoItem(
                    id = "2",
                    text = "Поздравить брата",
                    importance = Importance.Urgent,
                    deadline = LocalDateTime.of(2024, 6, 16, 12, 0),
                    isDone = false,
                    createdAt = LocalDateTime.now()
                ),
                TodoItem(
                    id = "0",
                    text = "Проснуться, улыбнуться",
                    importance = Importance.Low,
                    isDone = true,
                    createdAt = LocalDateTime.now()
                ),
                TodoItem(
                    id = "3",
                    text = "Зарядка утром",
                    importance = Importance.Low,
                    isDone = false,
                    createdAt = LocalDateTime.now(),
                    modifiedAt = LocalDateTime.now()
                )
            ), onDelete = {}, onNavigate = {}, onCheck = {}, onAddItem = {}, formatter = formatter
        )
    }
}
