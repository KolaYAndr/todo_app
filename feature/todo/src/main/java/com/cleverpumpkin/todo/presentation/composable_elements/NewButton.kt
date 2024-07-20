package com.cleverpumpkin.todo.presentation.composable_elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.cor.presentation.theme.TodoAppTheme
import com.cleverpumpkin.todo.R


@Composable
fun NewButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = TodoAppTheme.colorScheme.labelTertiary
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = stringResource(id = R.string.new_todo),
            style = TodoAppTheme.typography.button
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewNewButton() {
    NewButton(onClick = {  })
}
