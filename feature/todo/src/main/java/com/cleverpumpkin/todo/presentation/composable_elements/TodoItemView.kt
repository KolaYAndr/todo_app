package com.cleverpumpkin.todo.presentation.composable_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.cleverpumpkin.cor.presentation.theme.TodoAppTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.cleverpumpkin.todo.R

@Composable
fun TodoItemView(
    item: com.cleverpumpkin.todo.domain.todo_model.TodoItem,
    formatter: DateTimeFormatter,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Checkbox(
            modifier = Modifier.size(24.dp),
            checked = item.isDone,
            onCheckedChange = {
                onCheckedChange()
            },
            colors = CheckboxDefaults.colors(
                checkedColor = TodoAppTheme.colorScheme.green,
                uncheckedColor = when (item.importance) {
                    com.cleverpumpkin.todo.domain.todo_model.Importance.Urgent -> {
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
                        if (item.importance == com.cleverpumpkin.todo.domain.todo_model.Importance.Urgent) {
                            withStyle(
                                style = SpanStyle(
                                    color = TodoAppTheme.colorScheme.red,
                                    fontSize = 20.sp
                                )
                            ) {
                                append("!! ")
                            }
                        }
                        if (item.importance == com.cleverpumpkin.todo.domain.todo_model.Importance.Low) {
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

@PreviewLightDark
@Composable
fun PreviewItem() {
    TodoAppTheme {
        val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
        val item = com.cleverpumpkin.todo.domain.todo_model.TodoItem(
            id = "",
            text = "Ya",
            importance = com.cleverpumpkin.todo.domain.todo_model.Importance.Urgent,
            isDone = false,
            createdAt = LocalDateTime.now(),
            deadline = LocalDateTime.now()
        )
        TodoItemView(
            item,
            formatter,
            {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )
    }
}
