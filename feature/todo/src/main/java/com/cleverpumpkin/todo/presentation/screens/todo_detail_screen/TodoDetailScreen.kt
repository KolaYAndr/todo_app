package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import com.cleverpumpkin.cor.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R
import com.cleverpumpkin.todo.presentation.composable_elements.DeleteButton
import com.cleverpumpkin.todo.presentation.composable_elements.InputField
import com.cleverpumpkin.todo.presentation.composable_elements.RefreshBlock
import com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables.DeadlineBlock
import com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables.ImportanceBlock
import com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables.UsualTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    state: State<TodoDetailUiState>,
    onSave: () -> Unit,
    onNavBack: () -> Unit,
    onSelectImportance: (com.cleverpumpkin.todo.domain.todo_model.Importance) -> Unit,
    onSelectDeadline: (LocalDateTime?) -> Unit,
    onTextChange: (String) -> Unit,
    onDelete: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(modifier = modifier.background(TodoAppTheme.colorScheme.backPrimary),
        topBar = {
            UsualTopAppBar(
                onSave = { onSave() },
                onNavigate = { onNavBack() }
            )
        },
        snackbarHost = {
            if (uiState.errorCode != null) {
                val errorMessage = stringResource(
                    id = com.cleverpumpkin.todo.presentation.utils.getErrorStringResource(uiState.errorCode)
                )
                val actionMessage = stringResource(id = R.string.refresh)
                SnackbarHost(hostState = snackbarHostState)
                LaunchedEffect(Unit) {
                    scope.launch {
                        val result = snackbarHostState
                            .showSnackbar(
                                message = errorMessage,
                                actionLabel = actionMessage,
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                onRefresh()
                            }

                            SnackbarResult.Dismissed -> {
                                onNavBack()
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        when (uiState.errorCode) {
            null -> {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(TodoAppTheme.colorScheme.backPrimary)
                        .verticalScroll(scrollState)
                ) {
                    InputField(
                        onTextValueChange = { onTextChange(it) },
                        text = uiState.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    val importanceString = when (uiState.importance) {
                        com.cleverpumpkin.todo.domain.todo_model.Importance.Low -> R.string.importance_low
                        com.cleverpumpkin.todo.domain.todo_model.Importance.Normal -> R.string.importance_normal
                        com.cleverpumpkin.todo.domain.todo_model.Importance.Urgent -> R.string.importance_urgent
                    }
                    val importance = stringResource(id = importanceString)
                    val showDropdown = remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .clickable { showDropdown.value = true }
                            .padding(16.dp)
                    ) {
                        ImportanceBlock(
                            importanceText = importance,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                                .background(TodoAppTheme.colorScheme.backPrimary)
                        )

                        this@Column.AnimatedVisibility(visible = showDropdown.value) {
                            DropdownMenu(
                                modifier = Modifier.background(TodoAppTheme.colorScheme.backSecondary),
                                expanded = showDropdown.value,
                                onDismissRequest = { showDropdown.value = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.importance_low)) },
                                    onClick = {
                                        onSelectImportance(com.cleverpumpkin.todo.domain.todo_model.Importance.Low)
                                        showDropdown.value = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.importance_normal)) },
                                    onClick = {
                                        onSelectImportance(com.cleverpumpkin.todo.domain.todo_model.Importance.Normal)
                                        showDropdown.value = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(text = buildAnnotatedString {
                                            withStyle(style = SpanStyle(color = TodoAppTheme.colorScheme.red)) {
                                                append("!! ${stringResource(id = R.string.importance_urgent)}")
                                            }
                                        })
                                    },
                                    onClick = {
                                        onSelectImportance(com.cleverpumpkin.todo.domain.todo_model.Importance.Urgent)
                                        showDropdown.value = false
                                    }
                                )
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                    val datePickerState = rememberDatePickerState()
                    val showDatePicker = remember { mutableStateOf(false) }
                    val formatter =
                        remember { DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM) }
                    val pickedDate =
                        remember { mutableStateOf("") }
                    if (uiState.deadline != null) pickedDate.value =
                        formatter.format(uiState.deadline)

                    AnimatedVisibility(
                        visible = showDatePicker.value,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        DatePickerDialog(
                            onDismissRequest = {
                                showDatePicker.value = false
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDatePicker.value = false
                                        val timeInSeconds = (datePickerState.selectedDateMillis
                                            ?: datePickerState.displayedMonthMillis) / 1000
                                        val date = LocalDateTime.ofEpochSecond(
                                            timeInSeconds,
                                            0,
                                            ZoneOffset.UTC
                                        )
                                        pickedDate.value = formatter.format(date)
                                        onSelectDeadline(date)
                                    }
                                ) {
                                    Text(text = stringResource(id = R.string.ok))
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showDatePicker.value = false
                                    }
                                ) {
                                    Text(text = stringResource(id = R.string.cancel))
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    DeadlineBlock(
                        isDeadlineSet = uiState.deadline != null,
                        onSwitch = {
                            showDatePicker.value = it
                            if (!it) onSelectDeadline(null)
                        },
                        deadlineText = pickedDate.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                    DeleteButton(
                        onDelete = { onDelete() },
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            else -> RefreshBlock(
                errorCode = uiState.errorCode,
                onRefresh = { onRefresh() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}