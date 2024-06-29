package com.cleverpumpkin.todoapp.presentation.composable_elements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.todoapp.R
import com.cleverpumpkin.todoapp.presentation.theme.TodoAppTheme

@Composable
fun DeleteButton(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = { onDelete() },
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = TodoAppTheme.colorScheme.red,
            disabledContentColor = TodoAppTheme.colorScheme.labelDisable
        )
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.delete),
            contentDescription = stringResource(id = R.string.delete)
        )
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = stringResource(id = R.string.delete),
            style = TodoAppTheme.typography.button
        )
    }
}

@Preview
@Composable
fun PreviewDeleteButton() {
    DeleteButton(onDelete = {  })
}