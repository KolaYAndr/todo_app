package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R
import com.cleverpumpkin.todo.domain.todo_model.Importance
import com.cleverpumpkin.todo.presentation.composable_elements.DeleteButton
import com.cleverpumpkin.todo.presentation.composable_elements.InputField
import com.cleverpumpkin.todo.presentation.composable_elements.RefreshBlock
import com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables.DeadlineBlock
import com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables.ImportanceBlock
import com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables.ImportanceBottomSheet
import com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables.TopAppBarWithSave

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    state: State<TodoDetailUiState>,
    onSave: () -> Unit,
    onNavBack: () -> Unit,
    onSelectImportance: (Importance) -> Unit,
    onSelectDeadline: (LocalDateTime?) -> Unit,
    onTextChange: (String) -> Unit,
    onDelete: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = state.value
    Scaffold(
        modifier = modifier.background(TodoAppTheme.colorScheme.backPrimary),
        topBar = {
            TopAppBarWithSave(
                modifier = Modifier,
                onSave = { onSave() },
                onNavigate = { onNavBack() }
            )
        }
    ) { paddingValues ->
        when (uiState.errorCode) {
            null -> {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TodoAppTheme.colorScheme.backPrimary)
                        .padding(paddingValues)
                        .verticalScroll(scrollState)
                ) {
                    InputField(
                        onTextValueChange = { onTextChange(it) },
                        text = uiState.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    val importanceStringId = when (uiState.importance) {
                        Importance.Low -> R.string.importance_low
                        Importance.Normal -> R.string.importance_normal
                        Importance.Urgent -> R.string.importance_urgent
                    }
                    val importance = stringResource(id = importanceStringId)
                    var showDropdown by remember { mutableStateOf(false) }

                    ImportanceBlock(
                        importanceText = importance,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { showDropdown = true }
                            .background(TodoAppTheme.colorScheme.backPrimary)
                    )

                    val sheetState = rememberModalBottomSheetState(
                        skipPartiallyExpanded = true
                    )

                    this@Column.AnimatedVisibility(
                        visible = showDropdown,
                        enter = expandVertically(expandFrom = Alignment.Top),
                        exit = shrinkVertically(shrinkTowards = Alignment.Top)
                    ) {
                        ImportanceBottomSheet(
                            onSelectImportance = { onSelectImportance(it) },
                            sheetState = sheetState,
                            onDismiss = { showDropdown = false }
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = TodoAppTheme.colorScheme.supportSeparator
                    )

                    val datePickerState = rememberDatePickerState()
                    var showDatePicker by remember { mutableStateOf(false) }
                    val formatter =
                        remember { DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM) }
                    val pickedDate =
                        remember { mutableStateOf("") }
                    if (uiState.deadline != null) pickedDate.value =
                        formatter.format(uiState.deadline)

                    AnimatedVisibility(
                        visible = showDatePicker,
                        enter = expandVertically(expandFrom = Alignment.Top),
                        exit = shrinkVertically(shrinkTowards = Alignment.Top)
                    ) {
                        DatePickerDialog(
                            onDismissRequest = {
                                showDatePicker = false
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDatePicker = false
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
                                        showDatePicker = false
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
                            showDatePicker = it
                            if (!it) onSelectDeadline(null)
                        },
                        deadlineText = pickedDate.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = TodoAppTheme.colorScheme.supportSeparator
                    )

                    DeleteButton(
                        onDelete = { onDelete() },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.End)
                    )
                }
            }

            else -> RefreshBlock(
                errorCode = uiState.errorCode,
                onRefresh = { onRefresh() },
                modifier = Modifier
                    .fillMaxSize()
                    .background(TodoAppTheme.colorScheme.backPrimary)
                    .padding(paddingValues)
            )
        }
    }
}