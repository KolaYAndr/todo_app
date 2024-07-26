package com.cleverpumpkin.todo.presentation.composable_elements

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cleverpumpkin.core.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R
import com.cleverpumpkin.todo.domain.todo_model.Importance
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                val deadlineString = formatter.format(item.deadline)
                val semanticsString = stringResource(id = R.string.deadline_semantics, deadlineString)
                Text(
                    text = deadlineString,
                    color = TodoAppTheme.colorScheme.labelTertiary,
                    style = TodoAppTheme.typography.subhead,
                    modifier = Modifier.semantics {
                        contentDescription = semanticsString
                    }
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
            importance = Importance.Urgent,
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
