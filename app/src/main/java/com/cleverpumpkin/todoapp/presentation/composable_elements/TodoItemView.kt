package com.cleverpumpkin.todoapp.presentation.composable_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.domain.models.Importance
import com.cleverpumpkin.todoapp.domain.models.TodoItem
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TodoItemView(
    item: TodoItem,
    checked: MutableState<Boolean>,
    formatter: DateTimeFormatter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Checkbox(
            modifier = Modifier.size(24.dp),
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
                item.isDone = it
            },
            colors = CheckboxDefaults.colors(
                checkedColor = TodoAppTheme.colorScheme.green,
                uncheckedColor = when (item.importance) {
                    Importance.Urgent -> {
                        TodoAppTheme.colorScheme.red
                    }

                    else -> {
                        TodoAppTheme.colorScheme.supportSeparator
                    }
                },
                checkmarkColor = TodoAppTheme.colorScheme.backSecondary
            )
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top)
        ) {
            Text(
                text = buildAnnotatedString {
                    if (item.isDone) {
                        withStyle(
                            style = SpanStyle(
                                color = TodoAppTheme.colorScheme.labelTertiary,
                                textDecoration = TextDecoration.LineThrough
                            )
                        ) {
                            append(item.text)
                        }
                    } else {
                        if (item.importance == Importance.Urgent) {
                            withStyle(
                                style = SpanStyle(
                                    color = TodoAppTheme.colorScheme.red,
                                    fontSize = 20.sp
                                )
                            ) {
                                append("!! ")
                            }
                        }
                        if (item.importance == Importance.Low) {
                            withStyle(
                                style = SpanStyle(
                                    color = TodoAppTheme.colorScheme.gray, fontSize = 20.sp
                                )
                            ) {
                                append("↓ ")
                            }
                        }
                        append(item.text)
                    }
                },
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TodoAppTheme.typography.body,
                color = TodoAppTheme.colorScheme.labelPrimary
            )
            if (item.deadline != null) {
                Text(
                    text = formatter.format(item.deadline),
                    color = TodoAppTheme.colorScheme.labelTertiary,
                    style = TodoAppTheme.typography.subhead
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.info),
            contentDescription = stringResource(id = R.string.more_info),
            tint = TodoAppTheme.colorScheme.labelTertiary
        )
    }
}

@Preview
@Composable
fun PreviewItem() {
    val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
    val item = TodoItem(
        id = "",
        text = "Ya",
        importance = Importance.Urgent,
        isDone = false,
        createdAt = LocalDateTime.now(),
        deadline = LocalDateTime.now()
    )
    val checked = remember { mutableStateOf(item.isDone) }
    TodoItemView(
        item,
        checked,
        formatter,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    )
}