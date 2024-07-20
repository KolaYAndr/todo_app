package com.cleverpumpkin.todo.presentation.screens.todo_detail_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.cor.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R
import com.cleverpumpkin.todo.domain.todo_model.Importance

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportanceBottomSheet(
    onSelectImportance: (Importance) -> Unit,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = TodoAppTheme.colorScheme.backSecondary,
        contentColor = TodoAppTheme.colorScheme.labelPrimary,
        shape = RectangleShape,
        dragHandle = null,
        scrimColor = TodoAppTheme.colorScheme.supportSeparator,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp, top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HorizontalDivider(modifier = Modifier.width(60.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        onSelectImportance(Importance.Low)
                        onDismiss()
                    }
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.importance_low),
                style = TodoAppTheme.typography.body,
                color = TodoAppTheme.colorScheme.labelPrimary,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onSelectImportance(Importance.Normal)
                        onDismiss()
                    }
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.importance_normal),
                style = TodoAppTheme.typography.body,
                color = TodoAppTheme.colorScheme.labelPrimary,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onSelectImportance(Importance.Urgent)
                        onDismiss()
                    }
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = TodoAppTheme.colorScheme.red)) {
                        append(
                            "!! ${stringResource(id = R.string.importance_urgent)}"
                        )
                    }
                },
                style = TodoAppTheme.typography.body,
                color = TodoAppTheme.colorScheme.labelPrimary,
                textAlign = TextAlign.Center
            )
        }

    }
}